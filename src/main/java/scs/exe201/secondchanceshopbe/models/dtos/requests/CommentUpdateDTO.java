package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
public class CommentUpdateDTO {
    private long id;
    private String content;

}
