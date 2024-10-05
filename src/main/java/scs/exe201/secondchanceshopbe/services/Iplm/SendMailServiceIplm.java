package scs.exe201.secondchanceshopbe.services.Iplm;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.joran.spi.ActionException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import scs.exe201.secondchanceshopbe.services.SendMailService;

@Service
public class SendMailServiceIplm implements SendMailService {

    private String fromEmail = "hieunmse160501@fpt.edu.vn";

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(MultipartFile[] files, String to, String[] cc, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true để bật multipart

            helper.setFrom(fromEmail);
            helper.setTo(to);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }
            helper.setSubject(subject);
            helper.setText(body, true); // true để bật HTML content

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()) {
                            @Override
                            public String getFilename() {
                                return fileName; // Trả về tên file gốc
                            }
                        });
                    }
                }
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            new ActionException("Error sending email: " + e.getMessage());
        } catch (IOException exception) {
            new ActionException("Error sending email: " + exception.getMessage());
        }

    }

    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            String subject = "Verification Email";
            helper.setTo(toEmail);
            helper.setSubject(subject);
            String text = "Chào mừng " + toEmail + ",\n\n"
                    + "Vui lòng nhập otp sau để xác thực tài khoản của bạn:\n"
                    + "đây là otp của bạn:" + otp + "\n"
                    + "thank you:\n";
            helper.setText(text);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage());
        }
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Hàm gửi email xác thực
    // public void sendVerificationEmail(String to, String otp, String email) {
    //
    // }
    @Override
    public void sendVerificationEmail(String toEmail, String otp) {
        String subject = "Verification Email";
        String text = "Chào mừng " + toEmail + ",\n\n"
                + "Vui lòng nhập otp sau để xác thực tài khoản của bạn:\n"
                + "đây là otp của bạn:" + otp + "\n"
                + "thank you:\n";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
