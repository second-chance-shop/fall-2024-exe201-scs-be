package scs.exe201.secondchanceshopbe.services.iplm;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
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
 private final PasswordEncoder passwordEncoder;

    private final SendMailService mailSenderService;
    private final UserRepository userRepository;
    private Long timeOut = (long) 11.0;
    String vetify = "VETIFY";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void generateOTPCode(String email) {
        var value = generateRandomOTP();
        redisTemplate.opsForValue().set(email, value, timeOut, TimeUnit.MINUTES);
        mailSenderService.sendOtpEmail(email, value);
    }

    @Override
    public void changePasswordOtp(String email,  String newPassword) {
        var otp = generateRandomOTP();
        redisTemplate.opsForHash().put(email, "otp", otp);        // Lưu OTP vào field "otp"
        String password = passwordEncoder.encode(newPassword);
        redisTemplate.opsForHash().put(email, "password", password); // Lưu password vào field "password"
        redisTemplate.expire(email, timeOut, TimeUnit.MINUTES);
        mailSenderService.sendOtpEmail(email, otp);
    }

    @Override
    public void generateOTPCodeAgain(String email) {
        UserEntity check = userRepository.findByEmail(email).orElseThrow(
            ()->  new NotFoundException(email +" này chưa được đăng kí")
        );
        if(check.getStatus().equals(StatusEnum.DELETED)){
            throw  new ActionFailedException("account has been deleted");
        }
        if(check.getStatus().equals(StatusEnum.BAN)){
            throw new ActionFailedException("account has been ban");
        }
        if(check.getStatus().equals(StatusEnum.ACTIVE)){
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
    @Override
    public String verifyOtpSetPassword(OTPVerifyRequest request) {
        var otpInRedis = (String) redisTemplate.opsForHash().get(request.getEmail(), "otp");
        var password = (String) redisTemplate.opsForHash().get(request.getEmail(), "password");

        if (otpInRedis == null) {
            throw new ValidationFailedException("This OTP is not valid or has expired");
        }
        if (!otpInRedis.equals(request.getOtp())) {
            throw new ValidationFailedException("The OTP does not match");
        }
        return password;
    }

    @Override
    public void resendOTPSetPassword(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("user not found")
        );
        if(userEntity.getStatus().equals(StatusEnum.DELETED)){
            throw  new ActionFailedException("account has been deleted");
        }
        if(userEntity.getStatus().equals(StatusEnum.BAN)){
            throw new ActionFailedException("account has been ban");
        }
        if(userEntity.getStatus().equals(StatusEnum.VERIFY)){
            throw new ActionFailedException("account has been not verify");
        }
        var otpValue = generateRandomOTP();

        redisTemplate.opsForHash().put(email, "otp", otpValue);
        redisTemplate.expire(email, timeOut, TimeUnit.MINUTES);

        mailSenderService.sendOtpEmail(email, otpValue);
    }


    private String generateRandomOTP() {
        String otp;
        do {
            otp = RandomStringUtils.randomNumeric(6); 
        } while (redisTemplate.opsForValue().get(otp) != null);
        return otp;
    }

    
}


