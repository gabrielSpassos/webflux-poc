package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
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
}
