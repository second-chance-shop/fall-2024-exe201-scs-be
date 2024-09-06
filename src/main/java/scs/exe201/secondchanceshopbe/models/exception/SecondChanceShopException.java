package scs.exe201.secondchanceshopbe.models.exception;

import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;

public class SecondChanceShopException extends RuntimeException {
    protected ResponseObject errorResponse;

    protected SecondChanceShopException(String message) {
        super(message);
    }

    public ResponseObject getErrorResponse() {
        return errorResponse;
    }
}
