package com.gabrielspassos.poc.controller.v1.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CustomerRequest {

    @NotEmpty(message = "The email must not be empty")
    private String email;
    @NotEmpty(message = "The password must not be empty")
    private String password;
    @NotEmpty(message = "The name must not be empty")
    private String name;
    @NotEmpty(message = "The document must not be empty")
    private String document;

}
