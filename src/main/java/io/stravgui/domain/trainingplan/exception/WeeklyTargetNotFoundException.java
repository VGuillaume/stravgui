package io.stravgui.domain.trainingplan.exception;

import java.time.LocalDate;

public class WeeklyTargetNotFoundException extends TrainingPlanDomainException {

    private final LocalDate weekStart;

    public WeeklyTargetNotFoundException(LocalDate weekStart) {
        super("Aucun objectif trouvé pour la semaine du " + weekStart);
        this.weekStart = weekStart;
    }

    public LocalDate weekStart() {
        return weekStart;
    }
}