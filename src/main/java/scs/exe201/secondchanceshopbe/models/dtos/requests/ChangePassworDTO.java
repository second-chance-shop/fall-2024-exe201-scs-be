package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassworDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
