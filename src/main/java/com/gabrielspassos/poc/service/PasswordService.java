package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.config.CryptConfig;
import com.gabrielspassos.poc.crypt.CryptUtils;
import com.gabrielspassos.poc.exception.PasswordEncryptionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

import static com.gabrielspassos.poc.config.ErrorConstants.PASSWORD_DECRYPTION_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.PASSWORD_DECRYPTION_MESSAGE;
import static com.gabrielspassos.poc.config.ErrorConstants.PASSWORD_ENCRYPTION_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.PASSWORD_ENCRYPTION_MESSAGE;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordService {

    private static final Integer PASSWORD_LENGTH_SIZE = 2;
    private static final Integer PASSWORD_SIZE = 16;
    private static final String PASSWORD_FILLER = "F";
    private static final String SECRET_MODE = "DESede";
    private static final String CIPHER_MODE = "DESede/ECB/NoPadding";
    private CryptConfig cryptConfig;

    public Mono<String> encryptPassword(String password) {
        try {
            String passwordLength = StringUtils.leftPad(String.valueOf(password.length()), PASSWORD_LENGTH_SIZE, "0");
            password = passwordLength + password;
            String formattedPassword = StringUtils.rightPad(password, PASSWORD_SIZE, PASSWORD_FILLER);

            String encryptText = encryptText(formattedPassword, cryptConfig.getPasswordSecret());
            return Mono.just(encryptText);
        } catch (Exception e) {
            log.error("Error to encrypt password", e);
            throw new PasswordEncryptionException(PASSWORD_ENCRYPTION_CODE, PASSWORD_ENCRYPTION_MESSAGE);
        }
    }

    public Mono<String> decryptPassword(String encryptedPassword) {
        try {
            String decryptToText = decryptToText(encryptedPassword, cryptConfig.getPasswordSecret());

            Integer passwordLength = Integer.valueOf(decryptToText.substring(0, PASSWORD_LENGTH_SIZE));
            String password = decryptToText.substring(PASSWORD_LENGTH_SIZE, passwordLength + PASSWORD_LENGTH_SIZE);

            return Mono.just(password);
        } catch (Exception e) {
            log.error("Error to encrypt password", e);
            throw new PasswordEncryptionException(PASSWORD_DECRYPTION_CODE, PASSWORD_DECRYPTION_MESSAGE);
        }
    }

    private String encryptText(String textValueToEncrypt, String secretKey) throws Exception {
        Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(CryptUtils.pureTextToByteArray(textValueToEncrypt));
        return CryptUtils.byteArrayToHex(encryptedBytes);
    }

    private String decryptToText(String encryptedHex, String secretKey) throws Exception {
        Cipher cipher = createCipher(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(CryptUtils.hexStringToByteArray(encryptedHex));
        return CryptUtils.byteArrayToPureText(decryptedBytes);
    }

    private Cipher createCipher(Integer operationMode, String secretKey) throws Exception {
        byte[] keyAsBytes = CryptUtils.hexStringToByteArray(secretKey);
        KeySpec keySpec = new DESedeKeySpec(keyAsBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_MODE);

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);

        SecretKey key = keyFactory.generateSecret(keySpec);
        cipher.init(operationMode, key);
        return cipher;
    }
}
