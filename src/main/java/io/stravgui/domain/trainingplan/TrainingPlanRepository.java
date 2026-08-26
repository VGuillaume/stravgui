package io.stravgui.domain.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import java.util.Optional;

public interface TrainingPlanRepository {

    void save(TrainingPlan trainingPlan);

    Optional<TrainingPlan> findById(TrainingPlanId id);

    Optional<TrainingPlan> findByAthleteId(AthleteId athleteId);
}