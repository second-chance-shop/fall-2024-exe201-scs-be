package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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

