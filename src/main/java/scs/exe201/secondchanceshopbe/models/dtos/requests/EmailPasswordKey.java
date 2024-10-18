package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailPasswordKey {
   private String email;
   private String password;

}
