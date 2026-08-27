package io.stravgui.domain.athlete;

import java.util.Objects;

public class Athlete {

    private final AthleteId id;
    private OAuthCredentials stravaCredentials;

    private Athlete(AthleteId id, OAuthCredentials stravaCredentials) {
        this.id = id;
        this.stravaCredentials = stravaCredentials;
    }

    public static Athlete connectToStrava(OAuthCredentials credentials) {
        Objects.requireNonNull(credentials, "credentials ne peut pas être null");
        return new Athlete(AthleteId.generate(), credentials);
    }

    public static Athlete reconstitute(AthleteId id, OAuthCredentials stravaCredentials) {
        return new Athlete(id, stravaCredentials);
    }

    public void updateStravaCredentials(OAuthCredentials newCredentials) {
        this.stravaCredentials = Objects.requireNonNull(newCredentials);
    }

    public AthleteId id() { return id; }
    public OAuthCredentials stravaCredentials() { return stravaCredentials; }
}