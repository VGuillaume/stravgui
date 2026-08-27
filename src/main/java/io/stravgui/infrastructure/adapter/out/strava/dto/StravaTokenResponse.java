package io.stravgui.infrastructure.adapter.out.strava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.stravgui.domain.athlete.OAuthCredentials;
import java.time.Instant;

public record StravaTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("expires_at") long expiresAt) {

    public OAuthCredentials toOAuthCredentials() {
        return new OAuthCredentials(accessToken, refreshToken, Instant.ofEpochSecond(expiresAt));
    }
}