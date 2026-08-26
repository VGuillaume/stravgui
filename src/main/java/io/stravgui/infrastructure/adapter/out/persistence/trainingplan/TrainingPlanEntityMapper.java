package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanId;
import io.stravgui.domain.trainingplan.WeeklyTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TrainingPlanEntityMapper {

    TrainingPlanEntity toEntity(TrainingPlan domain) {
        TrainingPlanEntity entity = new TrainingPlanEntity(
                domain.getId().value(),
                domain.getAthleteId().value(),
                domain.getRaceDate()
        );
        domain.allTargets().forEach(target ->
                entity.addWeeklyTarget(new WeeklyTargetEntity(target.weekStart(), target.targetDistance().inKm()))
        );
        return entity;
    }

    TrainingPlan toDomain(TrainingPlanEntity entity) {
        List<WeeklyTarget> targets = entity.getWeeklyTargets().stream()
                .map(e -> new WeeklyTarget(e.getWeekStart(), Distance.ofKm(e.getTargetDistanceKm())))
                .toList();

        return TrainingPlan.reconstitute(
                new TrainingPlanId(entity.getId()),
                new AthleteId(entity.getAthleteId()),
                entity.getRaceDate(),
                targets
        );
    }
}