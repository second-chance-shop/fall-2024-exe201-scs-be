package scs.exe201.secondchanceshopbe.services.iplm;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.FileObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;
import scs.exe201.secondchanceshopbe.models.entities.FileCloud;
import scs.exe201.secondchanceshopbe.models.dtos.enums.FileCloudStatus;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.repositories.FileCloudRepository;
import scs.exe201.secondchanceshopbe.services.FileDatabaseService;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FileDatabaseServiceImpl implements FileDatabaseService {


    private final FileCloudRepository fileCloudRepository;
    private final AmazonS3 s3Service;
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Transactional(rollbackFor = {ActionFailedException.class})
    public FileObjectResponse uploadFile(MultipartFile file) {
        try {
            var uuid = UUID.randomUUID().toString();
            var nameUpdate = String.format("%s-%s", uuid, file.getOriginalFilename());
            var fileDb = FileCloud.builder()
                    .extension(file.getContentType())
                    .fileCloudId(uuid)
                    .fileName(nameUpdate)
                    .isRemoved(false)
                    .status(FileCloudStatus.UPLOADED)
                    .build();
            fileCloudRepository.save(fileDb);
            var fileObject = new FileObject();
            fileObject.setFile(file.getBytes());
            fileObject.setFileName(nameUpdate);
            return uploadFilev(fileObject);
        } catch (Exception ex) {
            throw new ActionFailedException("Failed to upload file");
        }
    }

    @Transactional(rollbackFor = {ActionFailedException.class})
    public List<FileObjectResponse> upNhieufile(List<MultipartFile> files) {
        try {
            if (files == null || files.isEmpty()) {
                throw new ActionFailedException("Failed to upload file: No files selected");
            }
            List<FileObject> fileObjects = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new ActionFailedException("File is empty");
                }

                String uuid = UUID.randomUUID().toString();
                String fileName = String.format("%s-%s", uuid, file.getOriginalFilename());

                FileCloud fileDb = FileCloud.builder()
                        .extension(file.getContentType())
                        .fileCloudId(uuid)
                        .fileName(fileName)
                        .isRemoved(false)
                        .status(FileCloudStatus.UPLOADED)
                        .build();

                fileCloudRepository.save(fileDb);

                FileObject fileObject = new FileObject();
                fileObject.setFile(file.getBytes());
                fileObject.setFileName(fileName);
                fileObjects.add(fileObject);
            }

            return interactFiles(s3Service -> {
                List<FileObjectResponse> listResponse = new ArrayList<>();

                for (FileObject file : fileObjects) {
                    try {
                        String fileName = file.getFileName();
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getFile());
                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(file.getFile().length);
                        metadata.setContentType(determineContentType(fileName));

                        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, metadata);
                        s3Service.putObject(request);
                        String url = s3Service.getUrl(bucket, fileName).toString();

                        listResponse.add(returnToFileObjectResponse(file, url));
                    } catch (AmazonS3Exception e) {
                        throw new ActionFailedException(e.getMessage());
                    }
                }
                return listResponse;
            });

        } catch (Exception ex) {
            throw new ActionFailedException("Failed to upload file: " + ex.getMessage());
        }
    }


    public FileObjectResponse uploadFilev(FileObject file) {
        var fileName = file.getFileName();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getFile());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getFile().length);
        metadata.setContentType(determineContentType(fileName));
        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, metadata);
        s3Service.putObject(request);
        var url = s3Service.getUrl(bucket, fileName).toString();
        return returnToFileObjectResponse(file, url);
    }

    public FileObjectResponse downloadFile(FileObject file) {
        var fileName = file.getFileName();
        try {
            S3Object s3Object = s3Service.getObject(bucket, fileName);
            InputStream stream = s3Object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] read_buf = new byte[1024];
            int read_len;
            while ((read_len = stream.read(read_buf)) > 0) {
                outputStream.write(read_buf, 0, read_len);
            }
            var response = new FileObjectResponse();
            response.setUrl(s3Service.getUrl(bucket, file.getFileName()).toString());
            response.setFileName(file.getFileName());
            response.setFile(outputStream.toByteArray());
            return response;
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                System.out.println("[ERROR] Can't Found File");
            } else if (e.getStatusCode() == 403) {
                System.out.println("[ERROR] Don't Have Permission");
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public FileObjectResponse interactFile(Function<AmazonS3, FileObjectResponse> callFunction) {
        return callFunction.apply(s3Service);
    }

    public List<FileObjectResponse> interactFiles(Function<AmazonS3, List<FileObjectResponse>> callFunction) {
        return callFunction.apply(s3Service);
    }

    public FileObjectResponse returnToFileObjectResponse(FileObject file, String url) {
        var response = new FileObjectResponse();
        response.setUrl(url);
        response.setFileName(file.getFileName());
        response.setFile(file.getFile());
        return response;
    }


    private String determineContentType(String filename) {
        if (filename.endsWith(".pdf")) {
            return "application/pdf";
        } else if (filename.endsWith(".webp")) {
            return "image/webp";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }
}
