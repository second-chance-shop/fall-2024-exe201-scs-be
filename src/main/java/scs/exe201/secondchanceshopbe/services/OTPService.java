package scs.exe201.secondchanceshopbe.services;

import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPVerifyRequest;

public interface OTPService {
    void verifyOTP(OTPVerifyRequest request);

    void generateOTPCode(String identity);

    void generateOTPCodeAgain(String identity);

    void changePasswordOtp(String email, String newPassword);

    String verifyOtpSetPassword(OTPVerifyRequest request);

    void resendOTPSetPassword(String email);
}
