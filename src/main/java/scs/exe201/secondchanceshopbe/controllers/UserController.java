package scs.exe201.secondchanceshopbe.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;
import scs.exe201.secondchanceshopbe.models.dtos.respones.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/registerNewUser")
    public ResponseEntity<?> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.registerNewUser(userRegisterDTO);
    }

    @GetMapping("/list-user")
    public ResponseEntity<ResponseObject> listUser() {
        List<UserResponse> userList = userService.getListUser();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("Get list user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userList)
                        .build()
        );
    }
}
