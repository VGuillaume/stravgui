package io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TrainingPlanResponse(
        UUID id,
        UUID athleteId,
        LocalDate raceDate,
        List<WeeklyTargetResponse> weeklyTargets
) {
}