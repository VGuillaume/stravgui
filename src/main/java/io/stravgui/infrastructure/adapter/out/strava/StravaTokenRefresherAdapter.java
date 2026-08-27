package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.domain.athlete.OAuthCredentials;
import io.stravgui.domain.athlete.StravaTokenRefresher;
import io.stravgui.infrastructure.adapter.out.strava.dto.StravaTokenResponse;
import org.springframework.stereotype.Component;

@Component
class StravaTokenRefresherAdapter implements StravaTokenRefresher {

    private final StravaOAuthClient oAuthClient;
    private final StravaProperties properties;

    StravaTokenRefresherAdapter(StravaOAuthClient oAuthClient, StravaProperties properties) {
        this.oAuthClient = oAuthClient;
        this.properties = properties;
    }

    @Override
    public OAuthCredentials refresh(OAuthCredentials expiredCredentials) {
        StravaTokenResponse response = oAuthClient.refreshToken(
                properties.clientId(), properties.clientSecret(),
                expiredCredentials.refreshToken(), "refresh_token"
        );
        return response.toOAuthCredentials();
    }
}