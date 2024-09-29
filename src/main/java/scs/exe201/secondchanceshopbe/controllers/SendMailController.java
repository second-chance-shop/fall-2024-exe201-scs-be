package scs.exe201.secondchanceshopbe.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.remoteconfig.internal.TemplateResponse.UserResponse;

import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.SendMailService;

@RestController
@RequestMapping("/api/v1/mail")
public class SendMailController {
    private final SendMailService sendMailService;

    public SendMailController(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }


    @PostMapping(value = "/sendMail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendMail(
            @RequestPart("files") MultipartFile[] files,
            @RequestParam(value = "to", required = true) String to,
            @RequestParam(value = "cc", required = false) String[] cc,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "body", required = true) String body) { 

        sendMailService.sendMail(files, to, cc, subject, body); 

         return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("create user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(null)
                        .build()
        );
    } 
}
