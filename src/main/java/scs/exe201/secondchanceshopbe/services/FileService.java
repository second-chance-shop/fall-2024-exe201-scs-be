package scs.exe201.secondchanceshopbe.services;

import com.amazonaws.services.s3.AmazonS3;
import scs.exe201.secondchanceshopbe.models.dtos.requests.FileObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;

import java.util.List;
import java.util.function.Function;

public interface FileService {
    FileObjectResponse uploadFile (FileObject file);
    FileObjectResponse returnToFileObjectResponse(FileObject file, String url);
    FileObjectResponse downloadFile(FileObject file);
    FileObjectResponse interactFile (Function<AmazonS3, FileObjectResponse> callFunction);
    List<FileObjectResponse> interactFiles (Function<AmazonS3, List<FileObjectResponse>> callFunction);
}
