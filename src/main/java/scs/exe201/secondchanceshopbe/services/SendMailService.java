package scs.exe201.secondchanceshopbe.services;

import org.springframework.web.multipart.MultipartFile;

public interface SendMailService {
    void sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
     void sendVerificationEmail(String toEmail,  String otp);
     void sendOtpEmail(String toEmail, String otp);

}
