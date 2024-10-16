package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scs.exe201.secondchanceshopbe.models.dtos.requests.FileObject;

@EqualsAndHashCode(callSuper = false)
@Data
public class FileObjectResponse extends FileObject {
    private String url;
}