package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowResponse {
    private long id;
    private long shopId;
    private long userId;
    private String status;
    private LocalDate dateFollowed;
}
