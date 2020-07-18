package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CustomerDTO {

    private String id;
    private String email;
    private String password;
    private String name;
    private String document;
    private CustomerStatusEnum status;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;

}
