package scs.exe201.secondchanceshopbe.services;

import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPVerifyRequest;

public interface OTPService {
    void verifyOTP(OTPVerifyRequest request);
    void generateOTPCode(String identity, String username);
    void generateOTPCodeAgain(String identity);
}
