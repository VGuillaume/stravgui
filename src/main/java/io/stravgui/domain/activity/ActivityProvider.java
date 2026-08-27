package io.stravgui.domain.activity;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.OAuthCredentials;
import java.util.List;

public interface ActivityProvider {
    List<Activity> fetchRecentActivities(AthleteId athleteId, OAuthCredentials credentials);
}