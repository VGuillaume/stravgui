package io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateTrainingPlanRequest(

        @NotNull(message = "raceDate est obligatoire")
        @Future(message = "raceDate doit être dans le futur")
        LocalDate raceDate

) {
}