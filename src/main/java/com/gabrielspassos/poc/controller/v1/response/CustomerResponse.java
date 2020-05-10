package com.gabrielspassos.poc.controller.v1.response;

import lombok.Data;

@Data
public class CustomerResponse {

    private String id;
    private String email;
    private String name;
    private String document;

}
