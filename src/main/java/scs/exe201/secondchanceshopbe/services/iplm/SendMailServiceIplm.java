package scs.exe201.secondchanceshopbe.services.iplm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
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
@RequiredArgsConstructor
public class SendMailServiceIplm implements SendMailService {

    private String fromEmail = "hieunmse160501@fpt.edu.vn";
    private final TemplateEngine templateEngine;

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
            Context context = new Context();
            context.setVariable("Email", toEmail);
            context.setVariable("OTP", otp);
            String content = templateEngine.process("SendOTPTemplate", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("OTP SECOND CHANCE SHOP");
            helper.setText(content, true); // Thiết lập nội dung email dưới dạng HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            throw new RuntimeException("Error while sending email: " + exception.getMessage());
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
    public static String loadEmailTemplate(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // Tạo nội dung email bằng cách thay thế các placeholder trong template
    public static String getOtpEmailContent(String toEmail, String otp, String filePath) throws IOException {
        // Đọc nội dung file HTML template
        String content = loadEmailTemplate(filePath);

        // Thay thế placeholder {{OTP}} và {{Email}} bằng dữ liệu thực tế
        content = content.replace("{{OTP}}", otp);
        content = content.replace("{{Email}}", toEmail);

        return content;
    }
}
