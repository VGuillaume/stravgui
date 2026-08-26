package io.stravgui.application.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanId;
import io.stravgui.domain.trainingplan.TrainingPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CreateTrainingPlanUseCase {

    private final TrainingPlanRepository trainingPlanRepository;

    public CreateTrainingPlanUseCase(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    @Transactional
    public TrainingPlanId execute(AthleteId athleteId, LocalDate raceDate) {
        TrainingPlan plan = TrainingPlan.create(athleteId, raceDate);
        trainingPlanRepository.save(plan);
        return plan.getId();
    }
}