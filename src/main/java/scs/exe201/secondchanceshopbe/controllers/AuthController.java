package scs.exe201.secondchanceshopbe.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;

import scs.exe201.secondchanceshopbe.services.AuthService;


@RestController
@RequestMapping("/api/v1/auth")

@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDTO loginDto) {
        JwtResponse jwtResponse = authService.authenticateUser(loginDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("AUTH_SUCCESS")
                        .message("Welcome To Second Chance Shop")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(jwtResponse)
                        .build()
        );
    }

}
