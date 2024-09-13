package scs.exe201.secondchanceshopbe.models.exception;

import org.springframework.http.HttpStatus;
import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;

public class AuthFailedException extends SecondChanceShopException {
    public AuthFailedException(final String message) {
    super(message);
        this.errorResponse = ResponseObject.builder()
            .code("AUTH_FAILED")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
}
}
