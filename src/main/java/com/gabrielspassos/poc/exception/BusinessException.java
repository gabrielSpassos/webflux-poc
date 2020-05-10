package com.gabrielspassos.poc.exception;

import com.gabrielspassos.poc.builder.dto.ErrorDTOBuilder;
import com.gabrielspassos.poc.dto.ErrorDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorDTO errorDTO;
    private HttpStatus status;

    public BusinessException(String code, String message, HttpStatus status) {
        this.errorDTO = ErrorDTOBuilder.build(code, message);
        this.status = status;
    }
}
