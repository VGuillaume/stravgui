package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.infrastructure.adapter.out.strava.dto.StravaTokenResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;

public interface StravaOAuthClient {

    @PostExchange
    StravaTokenResponse exchangeCodeForToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code,
            @RequestParam("grant_type") String grantType
    );

    @PostExchange
    StravaTokenResponse refreshToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("refresh_token") String refreshToken,
            @RequestParam("grant_type") String grantType
    );
}