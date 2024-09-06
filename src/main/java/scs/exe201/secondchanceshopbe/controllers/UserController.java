package scs.exe201.secondchanceshopbe.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
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
    public ResponseEntity<?> listUser() {
        List<UserEntity> userEntityList = userService.getListUser();
        return ResponseEntity.ok(userEntityList);
    }
}
