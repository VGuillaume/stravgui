package io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto;

import java.time.LocalDate;

public record WeeklyProgressResponse(
        LocalDate weekStart,
        LocalDate weekEnd,
        double targetDistanceKm,
        double actualDistanceKm,
        double completionRate,
        String status
) {
}