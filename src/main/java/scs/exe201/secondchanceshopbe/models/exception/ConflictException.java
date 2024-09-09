package scs.exe201.secondchanceshopbe.models.exception;

import org.springframework.http.HttpStatus;
import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;

public class ConflictException extends SecondChanceShopException{
    public ConflictException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("CONFLICT")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }
}
