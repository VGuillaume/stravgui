package io.stravgui.domain.athlete;

import java.time.Instant;
import java.util.Objects;

public record OAuthCredentials(String accessToken, String refreshToken, Instant expiresAt) {

    public OAuthCredentials {
        Objects.requireNonNull(accessToken, "accessToken ne peut pas être null");
        Objects.requireNonNull(refreshToken, "refreshToken ne peut pas être null");
        Objects.requireNonNull(expiresAt, "expiresAt ne peut pas être null");
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}