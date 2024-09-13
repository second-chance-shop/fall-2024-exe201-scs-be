package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private long commentId;
    private int start;
    private long userId;
    private long productId;
    private LocalDate createdAt;
}

