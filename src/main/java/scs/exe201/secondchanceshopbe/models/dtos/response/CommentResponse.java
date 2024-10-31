package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.*;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private long commentId;
    private String name;
    private String content;
    private long userId;
    private long productId;
    private LocalDate createdAt;
}
