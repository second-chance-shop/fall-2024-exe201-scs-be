package scs.exe201.secondchanceshopbe.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;
import scs.exe201.secondchanceshopbe.models.exception.AuthFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.models.exception.SecondChanceShopException;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            AuthFailedException.class,
            NotFoundException.class})
    public ResponseEntity<ResponseObject> handleSecondChanceShopException(SecondChanceShopException exception) {
        return ResponseEntity.status(exception.getErrorResponse().status())
                .body(exception.getErrorResponse());
    }


    @ExceptionHandler({
            BadCredentialsException.class,
            DisabledException.class,
            AuthenticationException.class
    })
    public ResponseEntity<ResponseObject> handleAuthenticationException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createAuthenticationErrorResponse(exception));
    }


    private ResponseObject createAuthenticationErrorResponse(RuntimeException exception) {
        return ResponseObject.builder()
                .isSuccess(false)
                .message(exception.getMessage())
                .code("AUTH_FAILED")
                .data(null)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }
}

