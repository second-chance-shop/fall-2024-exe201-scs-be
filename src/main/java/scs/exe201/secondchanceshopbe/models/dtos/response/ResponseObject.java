package scs.exe201.secondchanceshopbe.models.dtos.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseObject(
        Object data,
        String code,
        Boolean isSuccess,
        HttpStatus status,
        String message
) {
}