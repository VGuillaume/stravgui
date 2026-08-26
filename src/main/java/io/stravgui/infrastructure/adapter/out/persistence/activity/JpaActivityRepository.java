package io.stravgui.infrastructure.adapter.out.persistence.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface JpaActivityRepository extends JpaRepository<ActivityEntity, UUID> {

    List<ActivityEntity> findByAthleteId(UUID athleteId);

    boolean existsByStravaExternalId(String stravaExternalId);
}