package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import org.apache.commons.text.WordUtils;

public class CustomerEntityBuilder {

    public static CustomerEntity build(CustomerRequest customerRequest, String password) {
        String formattedName = WordUtils.capitalizeFully(customerRequest.getName());

        return CustomerEntity.builder()
                .email(customerRequest.getEmail())
                .password(password)
                .name(formattedName)
                .document(customerRequest.getDocument())
                .build();
    }

    public static CustomerEntity build(CustomerRequest customerRequest, String password, CustomerDTO customerDTO) {
        String formattedName = WordUtils.capitalizeFully(customerRequest.getName());

        return CustomerEntity.builder()
                .id(customerDTO.getId())
                .email(customerRequest.getEmail())
                .password(password)
                .name(formattedName)
                .document(customerRequest.getDocument())
                .build();
    }

    public static CustomerEntity build(CustomerDTO customerDTO) {
        return CustomerEntity.builder()
                .id(customerDTO.getId())
                .email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .name(customerDTO.getName())
                .document(customerDTO.getDocument())
                .build();
    }
}
