package scs.exe201.secondchanceshopbe.services;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;

import java.util.List;

public interface FileDatabaseService {
    FileObjectResponse uploadFile (MultipartFile file);

//    List<FileObjectResponse> uploadMultiFile(List<MultipartFile> file);

    List<FileObjectResponse> upNhieufile(List<MultipartFile> files);
}
