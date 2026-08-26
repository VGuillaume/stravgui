package io.stravgui.domain.trainingplan;

public enum ProgressStatus {
    EN_AVANCE,
    SUR_OBJECTIF,
    EN_RETARD;

    private static final double SEUIL_AVANCE = 1.1;
    private static final double SEUIL_SUR_OBJECTIF = 0.9;

    public static ProgressStatus from(double completionRate) {
        if (completionRate >= SEUIL_AVANCE) {
            return EN_AVANCE;
        }
        if (completionRate >= SEUIL_SUR_OBJECTIF) {
            return SUR_OBJECTIF;
        }
        return EN_RETARD;
    }
}