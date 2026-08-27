package io.stravgui.infrastructure.adapter.out.strava;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stravgui.strava")
public record StravaProperties(String clientId, String clientSecret, String redirectUri) {
}