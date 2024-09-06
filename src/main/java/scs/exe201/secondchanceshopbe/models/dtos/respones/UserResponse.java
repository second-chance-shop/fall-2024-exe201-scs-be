package scs.exe201.secondchanceshopbe.models.dtos.respones;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;

    private String username;

    private String address;

    private String gender;

    private String phoneNumber;

    private LocalDate dob;

    private String email;

    private String name;

    private String avatar;

    private String status;
}

