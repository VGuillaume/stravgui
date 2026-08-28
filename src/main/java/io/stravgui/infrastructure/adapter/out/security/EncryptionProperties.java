package io.stravgui.infrastructure.adapter.out.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stravgui.encryption")
public record EncryptionProperties(String secret, String salt) {
}