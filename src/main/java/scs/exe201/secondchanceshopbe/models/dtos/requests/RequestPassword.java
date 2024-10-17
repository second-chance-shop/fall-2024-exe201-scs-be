package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPassword {
    private String newPassword;
    private String confirmPassword;

}
