package scs.exe201.secondchanceshopbe.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateUserDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.services.OTPService;
import scs.exe201.secondchanceshopbe.services.UserService;
import org.springframework.web.bind.annotation.*;

import io.lettuce.core.RedisConnectionException;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OTPService otpService;
    @Transactional(rollbackFor = {RedisConnectionException.class} )
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserResponse userResponse = userService.registerNewUser(userRegisterDTO);
        otpService.generateOTPCode(userResponse.getEmail(),userRegisterDTO.getUsername());

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("create user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
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
    @PatchMapping ("/update-user")
    public ResponseEntity<ResponseObject> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        UserResponse userResponse = userService.userUpdate(updateUserDTO );
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Update user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }
    @DeleteMapping ("/delete-user{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        UserResponse userResponse = userService.userDelete(id );
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Update user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getUsers(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "userId", direction = Sort.Direction.DESC)
            })
            Pageable pageable)
    {
        UserResponse userResponse = userService.getUsers(search, pageable);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Update user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }
}
