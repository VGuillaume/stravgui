package io.stravgui.domain.activity;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import java.time.LocalDate;
import java.util.Objects;

public class Activity {

    private final ActivityId id;
    private final AthleteId athleteId;
    private final String stravaExternalId; // id brut Strava, pour dédoublonnage à la synchro
    private final ActivityType type;
    private final Distance distance;
    private final LocalDate startDate;

    public Activity(ActivityId id, AthleteId athleteId, String stravaExternalId,
                     ActivityType type, Distance distance, LocalDate startDate) {
        this.id = id;
        this.athleteId = athleteId;
        this.stravaExternalId = stravaExternalId;
        this.type = type;
        this.distance = distance;
        this.startDate = startDate;
    }

    public static Activity importFromStrava(AthleteId athleteId, String stravaExternalId,
                                            ActivityType type, Distance distance, LocalDate startDate) {
        Objects.requireNonNull(athleteId, "athleteId ne peut pas être null");
        Objects.requireNonNull(stravaExternalId, "stravaExternalId ne peut pas être null");
        return new Activity(ActivityId.generate(), athleteId, stravaExternalId, type, distance, startDate);
    }

    public static Activity reconstitute(ActivityId id, AthleteId athleteId, String stravaExternalId,
                                        ActivityType type, Distance distance, LocalDate startDate) {
        return new Activity(id, athleteId, stravaExternalId, type, distance, startDate);
    }

    public ActivityId id() { return id; }
    public AthleteId athleteId() { return athleteId; }
    public String stravaExternalId() { return stravaExternalId; }
    public ActivityType type() { return type; }
    public Distance distance() { return distance; }
    public LocalDate startDate() { return startDate; }

    public boolean isWithin(LocalDate weekStart, LocalDate weekEnd) {
        return !startDate.isBefore(weekStart) && !startDate.isAfter(weekEnd);
    }
}