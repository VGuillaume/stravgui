package io.stravgui.infrastructure.adapter.out.persistence.athlete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "athlete")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AthleteEntity {

    @Id
    private UUID id;

    @Column(name = "strava_access_token", nullable = false)
    private String accessToken;

    @Column(name = "strava_refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "strava_token_expires_at", nullable = false)
    private Instant expiresAt;
}