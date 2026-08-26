package io.stravgui.domain.activity;

import io.stravgui.domain.athlete.AthleteId;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {

    void save(Activity activity);

    Optional<Activity> findById(ActivityId id);

    List<Activity> findByAthlete(AthleteId athleteId);

    boolean existsByStravaExternalId(String stravaExternalId);
}