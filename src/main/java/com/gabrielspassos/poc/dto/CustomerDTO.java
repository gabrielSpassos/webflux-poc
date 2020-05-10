package com.gabrielspassos.poc.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDTO {

    private String id;
    private String email;
    private String password;
    private String name;
    private String document;

}
