package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.config.CryptConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @InjectMocks
    private PasswordService passwordService;
    @Mock
    private CryptConfig cryptConfig;

    private final String PASSWORD = "1020";
    private final String ENCRYPTED_PASSWORD = "2561E7C212C2C5457FCE74FBA5E5BCEC";

    @BeforeEach
    public void setup() {
        given(cryptConfig.getPasswordSecret()).willReturn("7DD84BBAF09D2AADE38ABCD58E070359CE3DE82DD965DAE5");
    }

    @Test
    public void shouldEncryptPassword() {
        String encryptedPassword = passwordService.encryptPassword(PASSWORD).block();

        assertEquals(ENCRYPTED_PASSWORD, encryptedPassword);
    }

    @Test
    public void shouldDecryptPassword() {
        String decryptedPassword = passwordService.decryptPassword(ENCRYPTED_PASSWORD).block();

        assertEquals(PASSWORD, decryptedPassword);
    }

}