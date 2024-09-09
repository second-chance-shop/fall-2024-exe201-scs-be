package scs.exe201.secondchanceshopbe.models.dtos.requests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @Pattern(regexp = "\\S+", message = "Username must not contain whitespace")
    @NotEmpty(message = "Username must not be empty")
    private String username;

    @Pattern(regexp = "\\S+", message = "Password must not contain whitespace")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotEmpty(message = "name not null!!!")
    private String name;

    @NotEmpty(message = "address not null!!!")
    private String address;

    @NotEmpty(message = "gender not null!!!")
    private String gender;

    @NotNull(message = "Dob not null!!!")
    @Past(message = "Dob must be in the past!!!")
    private LocalDate Dob;

    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must start with 0 and have 10 digits")
    @NotEmpty(message = "Phone number must not be empty")
    private String phoneNumber;

    @Pattern(regexp = "\\S+", message = "Email must not contain whitespace")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    private String avatar;

}
