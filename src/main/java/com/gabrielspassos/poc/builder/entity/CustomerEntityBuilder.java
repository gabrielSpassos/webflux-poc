package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import org.apache.commons.text.WordUtils;

import java.time.LocalDateTime;

import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.ACTIVE;

public class CustomerEntityBuilder {

    public static CustomerEntity build(CustomerRequest customerRequest, String password) {
        LocalDateTime now = LocalDateTime.now();
        String formattedName = WordUtils.capitalizeFully(customerRequest.getName());

        return CustomerEntity.builder()
                .email(customerRequest.getEmail())
                .password(password)
                .name(formattedName)
                .document(customerRequest.getDocument())
                .status(ACTIVE)
                .creationDateTime(now)
                .updateDateTime(now)
                .build();
    }

    public static CustomerEntity build(CustomerRequest customerRequest, String password, CustomerDTO customerDTO) {
        LocalDateTime now = LocalDateTime.now();
        String formattedName = WordUtils.capitalizeFully(customerRequest.getName());

        return CustomerEntity.builder()
                .id(customerDTO.getId())
                .email(customerRequest.getEmail())
                .password(password)
                .name(formattedName)
                .document(customerRequest.getDocument())
                .status(customerDTO.getStatus())
                .creationDateTime(customerDTO.getCreationDateTime())
                .updateDateTime(now)
                .build();
    }

    public static CustomerEntity build(CustomerDTO customerDTO, CustomerStatusEnum status) {
        LocalDateTime now = LocalDateTime.now();

        return CustomerEntity.builder()
                .id(customerDTO.getId())
                .email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .name(customerDTO.getName())
                .document(customerDTO.getDocument())
                .status(status)
                .creationDateTime(customerDTO.getCreationDateTime())
                .updateDateTime(now)
                .build();
    }
}
