package io.stravgui.application.trainingplan;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityRepository;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.trainingplan.*;
import io.stravgui.domain.trainingplan.exception.TrainingPlanNotFoundException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetWeeklyProgressUseCase {

    private final TrainingPlanRepository trainingPlanRepository;
    private final ActivityRepository activityRepository;
    private final WeeklyProgressCalculator calculator;

    public GetWeeklyProgressUseCase(TrainingPlanRepository trainingPlanRepository,
                                    ActivityRepository activityRepository,
                                    WeeklyProgressCalculator calculator) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.activityRepository = activityRepository;
        this.calculator = calculator;
    }

    @Transactional(readOnly = true)
    public WeeklyProgress execute(AthleteId athleteId, LocalDate weekStart) {
        TrainingPlan plan = trainingPlanRepository.findByAthleteId(athleteId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(athleteId));

        WeeklyTarget target = plan.targetForWeek(weekStart)
                .orElseThrow(() -> new WeeklyTargetNotFoundException(weekStart));

        List<Activity> activities = activityRepository.findByAthlete(athleteId);

        return calculator.calculate(target, activities);
    }
}