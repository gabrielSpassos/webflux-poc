package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;

import java.time.LocalDateTime;

public class EntityStub {

    public static CustomerEntity create() {
        return CustomerEntity.builder()
                .id("sddadshada")
                .email("gabriel@gmail.com")
                .document("16535378098")
                .name("Gabriel Silva Passos")
                .password("xxx")
                .status(CustomerStatusEnum.ACTIVE)
                .creationDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();
    }
}
