package com.gabrielspassos.poc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "customer")
public class CustomerEntity {

    @MongoId
    private Long id;
    private String email;
    private String password;
    private String name;
    private String document;

}
