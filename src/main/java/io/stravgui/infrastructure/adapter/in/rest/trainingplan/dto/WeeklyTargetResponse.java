package io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto;

import java.time.LocalDate;

public record WeeklyTargetResponse(LocalDate weekStart, double targetDistanceKm) {
}