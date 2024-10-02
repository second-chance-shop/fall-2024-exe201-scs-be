package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPVerifyRequest;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.OTPService;
import scs.exe201.secondchanceshopbe.services.UserService;

@RestController
@RequestMapping("/api/v1/OTP")
@RequiredArgsConstructor
public class OTPVerifyController {
    private final OTPService otpService;
    private final UserService userService;

    @PostMapping(path = "verify")
    public ResponseEntity<ResponseObject> verifyOTP (@RequestBody OTPVerifyRequest request) {
        otpService.verifyOTP(request);
        userService.ActiveUser(request.getEmail());
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }
    @PostMapping(path="reload-otp")
    public ResponseEntity<ResponseObject> reloadOtp(@RequestParam String email) {
        
        otpService.generateOTPCodeAgain(email);
        return ResponseEntity.ok(ResponseObject.builder()
                .code("VERIFY_SUCCESS")
                //.content(request)
                .message("Verify OTP Success")
                .isSuccess(true)
                .build());
    }
    
}
