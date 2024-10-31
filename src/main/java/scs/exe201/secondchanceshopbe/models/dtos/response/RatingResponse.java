package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private long ratingId;
    private int start;
    private long userId;
    private long productId;
    private LocalDate createdAt;
    private String status;
    private String name;
}

