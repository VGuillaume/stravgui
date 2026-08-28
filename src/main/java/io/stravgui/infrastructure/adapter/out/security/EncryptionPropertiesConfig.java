package io.stravgui.infrastructure.adapter.out.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EncryptionProperties.class)
class EncryptionPropertiesConfig {
}