package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.NotificationResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.NotificationService;

import java.util.List;

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
