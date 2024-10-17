package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.TemplateEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPVerifyRequest;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RequestPassword;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.OTPService;
import scs.exe201.secondchanceshopbe.services.UserService;

@RestController
@RequestMapping("/api/v1/OTP")
@RequiredArgsConstructor
public class OTPVerifyController {
    private final OTPService otpService;
    private final UserService userService;

    // dung chung cho forgetpassword va add user
    @PatchMapping(path = "verify")
    public ResponseEntity<ResponseObject> verifyOTP (@RequestBody OTPVerifyRequest request) {
        otpService.verifyOTP(request);
        userService.ActiveUser(request.getEmail());
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .status(HttpStatus.OK)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }

    @PatchMapping(path = "verify-set-password")
    public ResponseEntity<ResponseObject> verifyOTPSetPassword (@RequestBody OTPVerifyRequest request) {
        String newPassword = otpService.verifyOtpSetPassword(request);
        userService.setPassword(request.getEmail(), newPassword);
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .status(HttpStatus.OK)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }

    @PatchMapping(path = "verify-set-password-forget")
    public ResponseEntity<ResponseObject> verifyOTPSetPasswordForget (@RequestBody OTPVerifyRequest request,
                                                                      @RequestParam String newPassword,
                                                                      @RequestParam String confirmPassword) {
        otpService.verifyOTP(request);
        userService.setPasswordForget(request.getEmail(),newPassword,confirmPassword);

        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .status(HttpStatus.OK)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }

    @PostMapping(path="reload-otp")
    public ResponseEntity<ResponseObject> reloadOtp(@RequestParam String email) {
        otpService.generateOTPCodeAgain(email, TemplateEnum.ACCOUNT.toString());
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .status(HttpStatus.OK)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }

    @PostMapping(path="reload-otp-set-password")
    public ResponseEntity<ResponseObject> reloadOtpPassword(@RequestParam String email) {
        otpService.generateOTPCodeAgain(email,TemplateEnum.PASSWORD.toString());
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .status(HttpStatus.OK)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }
    
}
