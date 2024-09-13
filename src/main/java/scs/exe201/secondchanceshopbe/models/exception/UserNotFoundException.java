package scs.exe201.secondchanceshopbe.models.exception;

import org.springframework.http.HttpStatus;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;

public class UserNotFoundException extends SecondChanceShopException {
    public UserNotFoundException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("NOT_FOUND")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }
}
