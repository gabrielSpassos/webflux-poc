package com.gabrielspassos.poc.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CryptConfig {

    @Value("${secret.password}")
    private String passwordSecret;
}
