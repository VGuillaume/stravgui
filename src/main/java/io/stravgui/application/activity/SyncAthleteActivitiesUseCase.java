package io.stravgui.application.activity;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityProvider;
import io.stravgui.domain.activity.ActivityRepository;
import io.stravgui.domain.athlete.*;
import io.stravgui.domain.athlete.exception.AthleteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SyncAthleteActivitiesUseCase {

    private final AthleteRepository athleteRepository;
    private final ActivityRepository activityRepository;
    private final ActivityProvider activityProvider;
    private final StravaTokenRefresher tokenRefresher;

    public SyncAthleteActivitiesUseCase(AthleteRepository athleteRepository,
                                        ActivityRepository activityRepository,
                                        ActivityProvider activityProvider,
                                        StravaTokenRefresher tokenRefresher) {
        this.athleteRepository = athleteRepository;
        this.activityRepository = activityRepository;
        this.activityProvider = activityProvider;
        this.tokenRefresher = tokenRefresher;
    }

    @Transactional
    public int execute(AthleteId athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new AthleteNotFoundException(athleteId));

        OAuthCredentials credentials = ensureFreshCredentials(athlete);

        List<Activity> fetched = activityProvider.fetchRecentActivities(athleteId, credentials);

        List<Activity> newActivities = fetched.stream()
                .filter(activity -> !activityRepository.existsByStravaExternalId(activity.stravaExternalId()))
                .toList();

        newActivities.forEach(activityRepository::save);

        return newActivities.size();
    }

    private OAuthCredentials ensureFreshCredentials(Athlete athlete) {
        OAuthCredentials current = athlete.stravaCredentials();
        if (!current.isExpired()) {
            return current;
        }
        OAuthCredentials refreshed = tokenRefresher.refresh(current);
        athlete.updateStravaCredentials(refreshed);
        athleteRepository.save(athlete);
        return refreshed;
    }
}