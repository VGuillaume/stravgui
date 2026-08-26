package io.stravgui.domain.trainingplan.exception;

import java.time.LocalDate;

public class WeeklyTargetAfterRaceDateException extends TrainingPlanDomainException {

    public WeeklyTargetAfterRaceDateException(LocalDate weekStart, LocalDate raceDate) {
        super("La semaine du %s dépasse la date de course (%s)".formatted(weekStart, raceDate));
    }
}