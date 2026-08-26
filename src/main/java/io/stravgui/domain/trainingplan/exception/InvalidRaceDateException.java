package io.stravgui.domain.trainingplan.exception;

import java.time.LocalDate;

public class InvalidRaceDateException extends TrainingPlanDomainException {

    private final LocalDate weekStart;

    public InvalidRaceDateException(LocalDate weekStart) {
        super("La date de début de la course doit être dans le futur !" + weekStart);
        this.weekStart = weekStart;
    }

    public LocalDate weekStart() {
        return weekStart;
    }
}