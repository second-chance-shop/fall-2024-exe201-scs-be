package scs.exe201.secondchanceshopbe.models.dtos.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private long id;

    @NotEmpty(message = "name not null!!!")
    private String name;

    @NotEmpty(message = "address not null!!!")
    private String address;

    @NotEmpty(message = "gender not null!!!")
    private String gender;

    @NotNull(message = "Dob not null!!!")
    @Past(message = "Dob must be in the past!!!")
    private LocalDate Dob;

    private String avatar;

}
