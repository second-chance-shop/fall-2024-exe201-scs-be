package scs.exe201.secondchanceshopbe.services.Iplm;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPVerifyRequest;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.models.exception.ValidationFailedException;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.OTPService;
import scs.exe201.secondchanceshopbe.services.SendMailService;

@Service
@RequiredArgsConstructor
public class OtpServiceIplm implements OTPService {

    private final SendMailService mailSenderService;
    private final UserRepository userRepository;
    // private final TemplateEngine templateEngine;
    private Long timeOut = (long) 1.0;
    String vetify = "VETIFY";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void generateOTPCode(String email, String username) {
        var value = generateRandomOTP();

        redisTemplate.opsForValue().set(value, email, timeOut, TimeUnit.MINUTES);

        mailSenderService.sendOtpEmail(email, value);
    }

    @Override
    public void generateOTPCodeAgain(String email) {
        UserEntity check = userRepository.findByEmail(email).orElseThrow(
            ()->  new NotFoundException(email +" này chưa được đăng kí")
        );
        if(!check.getStatus().equals("VETIFY")){
            throw new ActionFailedException(email + "đã đăng kí rồi");
        }
        var value = generateRandomOTP();
        redisTemplate.opsForValue().set(value, email, timeOut, TimeUnit.MINUTES);
        mailSenderService.sendOtpEmail(email, value);
    }

    @Override
    public void verifyOTP(OTPVerifyRequest request) {
        var result = (String) redisTemplate.opsForValue().get(request.getOtp());
        if (result == null) {
            throw new ValidationFailedException("This OTP is not valid or expiration");
        }
        if (!result.equals(request.getEmail())) {
            throw new ValidationFailedException("The identity isn't match");
        }
    }

    private String generateRandomOTP() {
        String otp;
        do {
            otp = RandomStringUtils.randomNumeric(6); 
        } while (redisTemplate.opsForValue().get(otp) != null);
        return otp;
    }
    
}
