package scs.exe201.secondchanceshopbe.controllers;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.exception.*;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            AuthFailedException.class,
            NotFoundException.class,
            ConflictException.class,
            ActionFailedException.class})
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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> somethingWrongException(Exception ex) {
        var responseError = ResponseObject.builder()
                .isSuccess(false)
                .message(ex.getMessage())
                .code("SOMETHING_WRONG")
                .data(null)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
}

