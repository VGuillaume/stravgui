package io.stravgui.infrastructure.adapter.in.rest.strava;

import io.stravgui.application.athlete.ConnectAthleteToStravaUseCase;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.OAuthCredentials;
import io.stravgui.infrastructure.adapter.in.security.JwtService;
import io.stravgui.infrastructure.adapter.out.strava.StravaOAuthClient;
import io.stravgui.infrastructure.adapter.out.strava.StravaProperties;
import io.stravgui.infrastructure.adapter.out.strava.dto.StravaTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth/strava")
@Tag(name = "Strava OAuth", description = "Connexion d'un athlète à Strava via OAuth2")
public class StravaOAuthController {

    private final StravaProperties stravaProperties;
    private final StravaOAuthClient stravaOAuthClient;
    private final ConnectAthleteToStravaUseCase connectAthleteToStravaUseCase;
    private final JwtService jwtService;

    public StravaOAuthController(StravaProperties stravaProperties,
                                 StravaOAuthClient stravaOAuthClient,
                                 ConnectAthleteToStravaUseCase connectAthleteToStravaUseCase,
                                 JwtService jwtService) {
        this.stravaProperties = stravaProperties;
        this.stravaOAuthClient = stravaOAuthClient;
        this.connectAthleteToStravaUseCase = connectAthleteToStravaUseCase;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Redirige l'athlète vers la page de consentement Strava")
    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize() {
        String authorizeUrl = "https://www.strava.com/oauth/authorize"
                + "?client_id=" + stravaProperties.clientId()
                + "&redirect_uri=" + stravaProperties.redirectUri()
                + "&response_type=code"
                + "&scope=activity:read_all";

        return ResponseEntity.status(302).location(URI.create(authorizeUrl)).build();
    }

    @Operation(summary = "Callback appelé par Strava après consentement — échange le code contre des tokens")
    @GetMapping("/callback")
    public ConnectionResult callback(@RequestParam String code) {
        StravaTokenResponse tokenResponse = stravaOAuthClient.exchangeCodeForToken(
                stravaProperties.clientId(), stravaProperties.clientSecret(), code, "authorization_code");

        OAuthCredentials credentials = tokenResponse.toOAuthCredentials();
        AthleteId athleteId = connectAthleteToStravaUseCase.execute(credentials);

        String jwt = jwtService.generateToken(athleteId);
        return new ConnectionResult(athleteId.value().toString(), jwt);
    }

    public record ConnectionResult(String athleteId, String jwt) {
    }
}