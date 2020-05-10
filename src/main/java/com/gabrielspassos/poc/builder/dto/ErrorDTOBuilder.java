package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.ErrorDTO;

public class ErrorDTOBuilder {

    public static ErrorDTO build(String code, String message) {
        return new ErrorDTO(code, message);
    }
}
