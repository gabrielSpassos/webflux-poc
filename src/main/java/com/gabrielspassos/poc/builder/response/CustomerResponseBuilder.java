package com.gabrielspassos.poc.builder.response;

import com.gabrielspassos.poc.controller.v1.response.CustomerResponse;
import com.gabrielspassos.poc.dto.CustomerDTO;

public class CustomerResponseBuilder {

    public static CustomerResponse build(CustomerDTO customerDTO) {
        return CustomerResponse.builder()
                .id(customerDTO.getId())
                .email(customerDTO.getEmail())
                .name(customerDTO.getName())
                .document(customerDTO.getDocument())
                .status(customerDTO.getStatus().name())
                .creationDateTime(customerDTO.getCreationDateTime().toString())
                .updateDateTime(customerDTO.getUpdateDateTime().toString())
                .build();
    }
}
