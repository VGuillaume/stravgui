package io.stravgui.infrastructure.adapter.in.security;

import io.stravgui.domain.athlete.AthleteId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("local")
@Tag(name = "Dev Auth", description = "Génération de token JWT pour tests locaux — désactivé hors profil local")
public class DevAuthController {

    private final JwtService jwtService;

    public DevAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(summary = "Génère un JWT de test pour un athlète donné, sans passer par le flow OAuth Strava")
    @PostMapping("/api/auth/dev-token")
    public String generateDevToken(@RequestParam AthleteId athleteId) {
        return jwtService.generateToken(athleteId);
    }
}