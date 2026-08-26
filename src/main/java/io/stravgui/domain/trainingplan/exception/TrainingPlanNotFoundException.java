package io.stravgui.domain.trainingplan.exception;

import io.stravgui.domain.athlete.AthleteId;

public class TrainingPlanNotFoundException extends TrainingPlanDomainException {

    public TrainingPlanNotFoundException(AthleteId athleteId) {
        super("aucun plan d'entraînement trouvé pour l'athlète : %s".formatted(athleteId));
    }
}