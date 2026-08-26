package io.stravgui.domain.trainingplan.exception;

import java.time.LocalDate;

public class WeeklyTargetAlreadyExistsException extends TrainingPlanDomainException {

    private final LocalDate weekStart;

    public WeeklyTargetAlreadyExistsException(LocalDate weekStart) {
        super("Un objectif existe déjà pour la semaine du " + weekStart);
        this.weekStart = weekStart;
    }

    public LocalDate weekStart() {
        return weekStart;
    }
}