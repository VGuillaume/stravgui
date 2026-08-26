package io.stravgui.infrastructure.adapter.out.persistence.activity;

import io.stravgui.domain.activity.ActivityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "activity", uniqueConstraints = @UniqueConstraint(columnNames = "strava_external_id"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ActivityEntity {

    @Id
    private UUID id;

    @Column(name = "athlete_id", nullable = false)
    private UUID athleteId;

    @Column(name = "strava_external_id", nullable = false)
    private String stravaExternalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(name = "distance_km", nullable = false)
    private double distanceKm;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
}