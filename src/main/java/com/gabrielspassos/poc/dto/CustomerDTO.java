package com.gabrielspassos.poc.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String password;
    private String document;
    private String email;

}
