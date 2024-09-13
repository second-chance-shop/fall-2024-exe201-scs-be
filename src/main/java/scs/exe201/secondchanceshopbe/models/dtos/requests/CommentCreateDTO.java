package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
public class CommentCreateDTO {
    private String content;
    private long userId;
    private long productId;

}
