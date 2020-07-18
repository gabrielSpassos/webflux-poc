package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.entity.CustomerEntity;

public class EntityStub {

    public static CustomerEntity createWithoutId() {
        return CustomerEntity.builder()
                .email("gabriel@gmail.com")
                .document("16535378098")
                .name("Gabriel Silva Passos")
                .password("xxx")
                .build();
    }

    public static CustomerEntity create() {
        return CustomerEntity.builder()
                .id("sddadshada")
                .email("gabriel@gmail.com")
                .document("16535378098")
                .name("Gabriel Silva Passos")
                .password("xxx")
                .build();
    }
}
