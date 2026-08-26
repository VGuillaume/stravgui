package io.stravgui.application.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanRepository;
import io.stravgui.domain.trainingplan.WeeklyTarget;
import io.stravgui.domain.trainingplan.exception.TrainingPlanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AddWeeklyTargetUseCase {

    private final TrainingPlanRepository trainingPlanRepository;

    public AddWeeklyTargetUseCase(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    @Transactional
    public void execute(AthleteId athleteId, LocalDate weekStart, Distance targetDistance) {
        TrainingPlan plan = trainingPlanRepository.findByAthleteId(athleteId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(athleteId));

        plan.addWeeklyTarget(new WeeklyTarget(weekStart, targetDistance));

        trainingPlanRepository.save(plan);
    }
}