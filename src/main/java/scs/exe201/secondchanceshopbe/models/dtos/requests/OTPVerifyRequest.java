package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.Data;

@Data
public class OTPVerifyRequest {
    private long otp;
    private String email;
}
