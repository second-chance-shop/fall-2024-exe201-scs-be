package scs.exe201.secondchanceshopbe.models.exception;

import org.springframework.http.HttpStatus;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;

public class ValidationFailedException  extends SecondChanceShopException{
    public ValidationFailedException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("Validation_Failed")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }
}
