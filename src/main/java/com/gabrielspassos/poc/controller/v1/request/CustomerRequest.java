package com.gabrielspassos.poc.controller.v1.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private String email;
    private String password;
    private String name;
    private String document;

}
