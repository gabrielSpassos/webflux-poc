package com.gabrielspassos.poc.controller.v1.response;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String name;
    private String document;
    private String email;

}
