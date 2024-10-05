package scs.exe201.secondchanceshopbe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.NotificationResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.NotificationService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping()
    public ResponseEntity<ResponseObject> listUser() {
        List<NotificationResponse> notificationResponses = notificationService.getAllNotification();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("Get list successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(notificationResponses)
                        .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestBody NotificationCreateDTO notificationCreateDTO) {
        NotificationResponse notificationResponses = notificationService.createNotification( notificationCreateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("Get list successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(notificationResponses)
                        .build()
        );
    }
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody NotificationUpdateDTO notificationUpdateDTO) {
        NotificationResponse notificationResponses = notificationService.updateNotification( notificationUpdateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("Get list successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(notificationResponses)
                        .build()
        );
    }
    @DeleteMapping("delete{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable long id) {
        NotificationResponse notificationResponses = notificationService.deleteNotification( id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("Get list successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(notificationResponses)
                        .build()
        );
    }
}
