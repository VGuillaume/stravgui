package io.stravgui.domain.athlete.exception;

import io.stravgui.domain.athlete.AthleteId;

public class AthleteNotFoundException extends AthleteDomainException {

    private final AthleteId athleteId;

    public AthleteNotFoundException(AthleteId athleteId) {
        super("Aucun athlète trouvé pour la semaine du " + athleteId);
        this.athleteId = athleteId;
    }

    public AthleteId weekStart() {
        return athleteId;
    }
}