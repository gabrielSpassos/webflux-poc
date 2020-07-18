package com.gabrielspassos.poc.controller.v1.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerResponse {

    private String id;
    private String email;
    private String name;
    private String document;
    private String status;
    private String creationDateTime;
    private String updateDateTime;

}
