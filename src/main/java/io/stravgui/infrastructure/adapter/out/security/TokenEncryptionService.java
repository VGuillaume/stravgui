package io.stravgui.infrastructure.adapter.out.security;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class TokenEncryptionService {

    private final TextEncryptor encryptor;

    public TokenEncryptionService(EncryptionProperties properties) {
        this.encryptor = Encryptors.delux(properties.secret(), properties.salt());
    }

    public String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public String decrypt(String cipherText) {
        return encryptor.decrypt(cipherText);
    }
}