package io.stravgui.application.activity;

import io.stravgui.domain.activity.*;
import io.stravgui.domain.athlete.*;
import io.stravgui.domain.athlete.exception.AthleteNotFoundException;
import io.stravgui.domain.shared.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SyncAthleteActivitiesUseCaseTest {

    @Mock private AthleteRepository athleteRepository;
    @Mock private ActivityRepository activityRepository;
    @Mock private ActivityProvider activityProvider;
    @Mock private StravaTokenRefresher tokenRefresher;

    private SyncAthleteActivitiesUseCase useCase;

    private AthleteId athleteId;
    private OAuthCredentials validCredentials;

    @BeforeEach
    void setUp() {
        useCase = new SyncAthleteActivitiesUseCase(athleteRepository, activityRepository, activityProvider, tokenRefresher);
        athleteId = AthleteId.generate();
        validCredentials = new OAuthCredentials("access-token", "refresh-token", Instant.now().plus(1, ChronoUnit.HOURS));
    }

    private Activity activityWith(String stravaExternalId) {
        return Activity.reconstitute(
                ActivityId.generate(), athleteId, stravaExternalId,
                ActivityType.RUN, Distance.ofKm(10), LocalDate.of(2026, 8, 19)
        );
    }

    @Test
    void execute_throws_when_athlete_does_not_exist() {
        when(athleteRepository.findById(athleteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(athleteId))
                .isInstanceOf(AthleteNotFoundException.class);

        verifyNoInteractions(activityProvider, activityRepository);
    }

    @Test
    void execute_saves_only_new_activities_and_returns_their_count() {
        Athlete athlete = Athlete.reconstitute(athleteId, validCredentials);
        when(athleteRepository.findById(athleteId)).thenReturn(Optional.of(athlete));

        Activity alreadyKnown = activityWith("strava-1");
        Activity brandNew = activityWith("strava-2");
        when(activityProvider.fetchRecentActivities(athleteId, validCredentials))
                .thenReturn(List.of(alreadyKnown, brandNew));

        when(activityRepository.existsByStravaExternalId("strava-1")).thenReturn(true);
        when(activityRepository.existsByStravaExternalId("strava-2")).thenReturn(false);

        int importedCount = useCase.execute(athleteId);

        assertThat(importedCount).isEqualTo(1);
        verify(activityRepository).save(brandNew);
        verify(activityRepository, never()).save(alreadyKnown);
    }

    @Test
    void execute_does_not_refresh_token_when_credentials_are_still_valid() {
        Athlete athlete = Athlete.reconstitute(athleteId, validCredentials);
        when(athleteRepository.findById(athleteId)).thenReturn(Optional.of(athlete));
        when(activityProvider.fetchRecentActivities(any(), any())).thenReturn(List.of());

        useCase.execute(athleteId);

        verifyNoInteractions(tokenRefresher);
        verify(activityProvider).fetchRecentActivities(athleteId, validCredentials);
    }

    @Test
    void execute_refreshes_the_token_when_credentials_are_expired_before_fetching_activities() {
        OAuthCredentials expiredCredentials = new OAuthCredentials(
                "old-access", "old-refresh", Instant.now().minus(1, ChronoUnit.HOURS));
        Athlete athlete = Athlete.reconstitute(athleteId, expiredCredentials);
        when(athleteRepository.findById(athleteId)).thenReturn(Optional.of(athlete));

        OAuthCredentials refreshedCredentials = new OAuthCredentials(
                "new-access", "new-refresh", Instant.now().plus(1, ChronoUnit.HOURS));
        when(tokenRefresher.refresh(expiredCredentials)).thenReturn(refreshedCredentials);

        when(activityProvider.fetchRecentActivities(athleteId, refreshedCredentials)).thenReturn(List.of());

        useCase.execute(athleteId);

        verify(tokenRefresher).refresh(expiredCredentials);
        verify(athleteRepository).save(athlete); // persistance des nouveaux tokens
        verify(activityProvider).fetchRecentActivities(athleteId, refreshedCredentials); // pas l'ancien token
    }
}