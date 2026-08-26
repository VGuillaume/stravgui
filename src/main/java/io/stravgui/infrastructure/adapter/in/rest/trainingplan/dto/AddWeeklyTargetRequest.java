package io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AddWeeklyTargetRequest(

        @NotNull(message = "weekStart est obligatoire")
        LocalDate weekStart,

        @NotNull(message = "targetDistanceKm est obligatoire")
        @Positive(message = "targetDistanceKm doit être positif")
        Double targetDistanceKm

) {
}