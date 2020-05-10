package com.gabrielspassos.poc.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PasswordService {

    public Mono<String> encryptPassword(String password) {
        return Mono.just("foo");
    }

    public Mono<String> decryptPassword(String password) {
        return Mono.just("bar");
    }
}
