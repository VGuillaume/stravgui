package io.stravgui.infrastructure.adapter.in.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.stravgui.domain.athlete.AthleteId;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMinutes;

    public JwtService(JwtProperties properties) {
        this.signingKey = Keys.hmacShaKeyFor(properties.secret().getBytes());
        this.expirationMinutes = properties.expirationMinutes();
    }

    public String generateToken(AthleteId athleteId) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(athleteId.value().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(signingKey)
                .compact();
    }

    public AthleteId validateAndExtractAthleteId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return AthleteId.of(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException("Token JWT invalide ou expiré", e);
        }
    }
}