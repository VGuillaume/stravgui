package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityProvider;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.OAuthCredentials;
import io.stravgui.domain.shared.Distance;
import io.stravgui.infrastructure.adapter.out.strava.dto.StravaActivityResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StravaActivityProviderAdapter implements ActivityProvider {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PER_PAGE = 30;

    private final StravaActivityClient stravaActivityClient;

    StravaActivityProviderAdapter(StravaActivityClient stravaActivityClient) {
        this.stravaActivityClient = stravaActivityClient;
    }

    @Override
    public List<Activity> fetchRecentActivities(AthleteId athleteId, OAuthCredentials credentials) {
        List<StravaActivityResponse> rawActivities = stravaActivityClient.getActivities(
                "Bearer " + credentials.accessToken(), DEFAULT_PAGE, DEFAULT_PER_PAGE
        );

        return rawActivities.stream()
                .map(raw -> Activity.importFromStrava(
                        athleteId,
                        String.valueOf(raw.stravaId()),
                        StravaTypeMapper.toDomainType(raw.type()),
                        Distance.ofMeters(raw.distanceMeters()),
                        StravaTypeMapper.toLocalDate(raw.startDateLocal())
                ))
                .toList();
    }
}