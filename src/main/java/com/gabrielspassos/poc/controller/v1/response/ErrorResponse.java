package com.gabrielspassos.poc.controller.v1.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;
    private String code;
}
