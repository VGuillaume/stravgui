package io.stravgui.infrastructure.adapter.in.security;

import io.stravgui.domain.athlete.AthleteId;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

public class AthleteAuthenticationToken extends AbstractAuthenticationToken {

    private final AthleteId athleteId;

    public AthleteAuthenticationToken(AthleteId athleteId) {
        super(List.of()); // aucune "authority"/rôle pour l'instant — pas de notion de rôle dans ton domaine
        this.athleteId = athleteId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // pas de mot de passe à exposer, le token JWT a déjà été vérifié en amont
    }

    @Override
    public Object getPrincipal() {
        return athleteId; // le "principal" est directement ton AthleteId typé
    }

    public AthleteId athleteId() {
        return athleteId;
    }
}