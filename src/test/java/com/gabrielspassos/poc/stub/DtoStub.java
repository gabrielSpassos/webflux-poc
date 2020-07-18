package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;

import java.time.LocalDateTime;

public class DtoStub {

    public static CustomerDTO create() {
        return CustomerDTO.builder()
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
