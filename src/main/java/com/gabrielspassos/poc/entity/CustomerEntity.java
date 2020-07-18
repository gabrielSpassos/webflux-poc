package com.gabrielspassos.poc.entity;

import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "customer")
public class CustomerEntity {

    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String document;
    private CustomerStatusEnum status;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;

}
