package com.gabrielspassos.poc.controller.v1.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private Long id;
    private String name;
    private String password;
    private String document;
    private String email;

}
