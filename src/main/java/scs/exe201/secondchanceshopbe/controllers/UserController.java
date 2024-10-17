package scs.exe201.secondchanceshopbe.controllers;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.lettuce.core.RedisConnectionException;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ChangePassworDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.services.OTPService;
import scs.exe201.secondchanceshopbe.services.UserService;

@RequestMapping("/api/v1/user")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OTPService otpService;


    @Transactional(rollbackFor = {RedisConnectionException.class})
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> register(@RequestPart("user") UserRegisterDTO userRegisterDTO,
                                                   @RequestPart(value = "file", required = false) MultipartFile image) {
        UserResponse userResponse = userService.registerUser(userRegisterDTO, image);

        otpService.generateOTPCode(userResponse.getEmail());
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

    @PutMapping(path = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> update(@RequestPart("user") UserUpdateDTO updateUserDTO,
                                                 @RequestPart(value = "file", required = false) MultipartFile image) {
        UserResponse userResponse = userService.updateUser(updateUserDTO, image);
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserId(@RequestParam long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("Get user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        UserResponse userResponse = userService.userDelete(id);
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
            Pageable pageable) {
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

    @GetMapping("/profile")
    public ResponseEntity<ResponseObject> getUserProfile() {
        UserResponse userResponse = userService.getUserProfile();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("Get user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }

    @PutMapping(path = "change-password")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePassworDTO changePassworDTO) {
        UserResponse userResponse = userService.changPassword(
                changePassworDTO.getEmail(),
                changePassworDTO.getOldPassword(),
                changePassworDTO.getNewPassword(),
                changePassworDTO.getNewPasswordConfirm());
        otpService.changePasswordOtp(userResponse.getEmail(), changePassworDTO.getNewPassword());
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("Get user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(userResponse)
                        .build()
        );
    }

}
