package scs.exe201.secondchanceshopbe.services.iplm;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.enums.TemplateEnum;
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
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void generateOTPCode(String email, String template) {
        var value = generateRandomOTP();
        redisTemplate.opsForValue().set(email, value, timeOut, TimeUnit.MINUTES); // Sử dụng email làm key
        mailSenderService.sendOtpEmail(email, value, template);
    }

    @Override
    public void changePasswordOtp(String email, String newPassword) {
        String template = TemplateEnum.PASSWORD.toString();
        var otp = generateRandomOTP();
        String encodedPassword = passwordEncoder.encode(newPassword);
        redisTemplate.opsForHash().put(email, "otp", otp);
        redisTemplate.opsForHash().put(email, "password", encodedPassword);
        redisTemplate.expire(email, timeOut, TimeUnit.MINUTES);
        mailSenderService.sendOtpEmail(email, otp, template);
    }


    @Override
    public void generateOTPCodeAgain(String email, String template) {
        if (template == null) {
            throw new ActionFailedException("Template is null");
        }
        UserEntity check = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(email + " chưa được đăng kí")
        );
        if (check.getStatus().equals(StatusEnum.DELETED)) {
            throw new ActionFailedException("Account has been deleted");
        }
        if (check.getStatus().equals(StatusEnum.BAN)) {
            throw new ActionFailedException("Account has been banned");
        }
        if (!TemplateEnum.PASSWORD.toString().equals(template)) {
            if (check.getStatus().equals(StatusEnum.ACTIVE)) {
                throw new ActionFailedException(email + " đã đăng kí rồi");
            }
        }

        var otpValue = generateRandomOTP();
        redisTemplate.opsForHash().put(email, "otp", otpValue); // Lưu OTP vào Redis với email làm key
        redisTemplate.expire(email, timeOut, TimeUnit.MINUTES); // Đặt thời gian hết hạn
        mailSenderService.sendOtpEmail(email, otpValue, template);
    }


    @Override
    public void verifyOTP(OTPVerifyRequest request) {
        var storedOtp = (String) redisTemplate.opsForHash().get(request.getEmail(), "otp"); // Lấy OTP từ opsForHash
        if (storedOtp == null) {
            throw new ValidationFailedException("This OTP is not valid or expired");
        }
        if (!storedOtp.equals(request.getOtp())) {
            throw new ValidationFailedException("The OTP doesn't match");
        }
    }

    @Override
    public String verifyOtpSetPassword(OTPVerifyRequest request) {
        var otpInRedis = (String) redisTemplate.opsForHash().get(request.getEmail(), "otp"); // Lấy OTP từ opsForHash
        var password = (String) redisTemplate.opsForHash().get(request.getEmail(), "password"); // Lấy mật khẩu từ opsForHash

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
                () -> new NotFoundException("User not found")
        );
        if (userEntity.getStatus().equals(StatusEnum.DELETED)) {
            throw new ActionFailedException("Account has been deleted");
        }
        if (userEntity.getStatus().equals(StatusEnum.BAN)) {
            throw new ActionFailedException("Account has been banned");
        }
        if (userEntity.getStatus().equals(StatusEnum.VERIFY)) {
            throw new ActionFailedException("Account has not been verified");
        }

        var otpValue = generateRandomOTP();
        String template = TemplateEnum.PASSWORD.toString();
        redisTemplate.opsForHash().put(email, "otp", otpValue); // Lưu OTP vào Redis với email làm key
        redisTemplate.expire(email, timeOut, TimeUnit.MINUTES); // Đặt thời gian hết hạn
        mailSenderService.sendOtpEmail(email, otpValue, template);
    }

    private String generateRandomOTP() {
        String otp;
        do {
            otp = RandomStringUtils.randomNumeric(6); 
        } while (redisTemplate.opsForValue().get(otp) != null);
        return otp;
    }
}


