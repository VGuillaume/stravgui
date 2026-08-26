package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

interface JpaTrainingPlanRepository extends JpaRepository<TrainingPlanEntity, UUID> {

    Optional<TrainingPlanEntity> findByAthleteId(UUID athleteId);

    Optional<TrainingPlanEntity> findByRaceDate(LocalDate raceDate);
}