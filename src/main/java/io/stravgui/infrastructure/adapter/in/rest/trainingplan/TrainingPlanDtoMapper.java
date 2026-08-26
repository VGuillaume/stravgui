package io.stravgui.infrastructure.adapter.in.rest.trainingplan;

import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.WeeklyProgress;
import io.stravgui.domain.trainingplan.WeeklyTarget;
import io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto.TrainingPlanResponse;
import io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto.WeeklyProgressResponse;
import io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto.WeeklyTargetResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TrainingPlanDtoMapper {

    TrainingPlanResponse toResponse(TrainingPlan plan) {
        List<WeeklyTargetResponse> targets = plan.getWeeklyTargets().stream()
                .map(t -> new WeeklyTargetResponse(t.weekStart(), t.targetDistance().inKm()))
                .toList();

        return new TrainingPlanResponse(
                plan.getId().value(),
                plan.getAthleteId().value(),
                plan.getRaceDate(),
                targets
        );
    }

    WeeklyProgressResponse toResponse(WeeklyProgress progress) {
        WeeklyTarget target = progress.target();
        return new WeeklyProgressResponse(
                target.weekStart(),
                target.weekEnd(),
                target.targetDistance().inKm(),
                progress.actualDistance().inKm(),
                progress.completionRate(),
                progress.status().name()
        );
    }
}