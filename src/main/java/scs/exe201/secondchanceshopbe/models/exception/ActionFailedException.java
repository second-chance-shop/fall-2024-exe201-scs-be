package scs.exe201.secondchanceshopbe.models.exception;

import org.springframework.http.HttpStatus;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;

public class ActionFailedException extends SecondChanceShopException{
    public ActionFailedException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("ACTION_FAILED")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }

    public ActionFailedException(String message, String code) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code(code)
                .data(null)
                .message(message)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }
}
