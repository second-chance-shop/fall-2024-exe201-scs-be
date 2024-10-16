package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.Data;

@Data
public class FileObject {
    private String fileName;
    private byte[] file;
    private String path;
}
