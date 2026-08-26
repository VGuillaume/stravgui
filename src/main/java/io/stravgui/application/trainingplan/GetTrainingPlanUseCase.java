package io.stravgui.application.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanRepository;
import io.stravgui.domain.trainingplan.exception.TrainingPlanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetTrainingPlanUseCase {

    private final TrainingPlanRepository trainingPlanRepository;

    public GetTrainingPlanUseCase(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    @Transactional(readOnly = true)
    public TrainingPlan execute(AthleteId athleteId) {
        return trainingPlanRepository.findByAthleteId(athleteId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(athleteId));
    }
}