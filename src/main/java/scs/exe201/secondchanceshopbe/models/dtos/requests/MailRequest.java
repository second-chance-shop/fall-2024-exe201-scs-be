package scs.exe201.secondchanceshopbe.models.dtos.requests;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    @ArraySchema(schema = @Schema(type = "string", format = "binary"))
    private MultipartFile[] files;
    private String subject;
    private String body;
    private String to;
    private String[] cc;

}
