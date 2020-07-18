package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;

public class CustomerDTOBuilder {

    public static CustomerDTO build(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .password(customerEntity.getPassword())
                .name(customerEntity.getName())
                .document(customerEntity.getDocument())
                .status(customerEntity.getStatus())
                .creationDateTime(customerEntity.getCreationDateTime())
                .updateDateTime(customerEntity.getUpdateDateTime())
                .build();
    }
}
