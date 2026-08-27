package io.stravgui.domain.trainingplan;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityId;
import io.stravgui.domain.activity.ActivityType;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class WeeklyProgressCalculatorTest {

    private final WeeklyProgressCalculator calculator = new WeeklyProgressCalculator();
    private final LocalDate weekStart = LocalDate.of(2026, 8, 17); // un lundi
    private final WeeklyTarget target = new WeeklyTarget(weekStart, Distance.ofKm(20));
    private final AthleteId athleteId = AthleteId.generate();

    private Activity activityOn(LocalDate date, double km) {
        return Activity.reconstitute(
                ActivityId.generate(),
                athleteId,
                "strava-" + date,
                ActivityType.RUN,
                Distance.ofKm(km),
                date
        );
    }

    @Test
    void calculate_sums_only_activities_within_the_target_week() {
        Activity activityInsideWeek = activityOn(LocalDate.of(2026, 8, 19), 8);
        Activity anotherActivityInsideWeek = activityOn(LocalDate.of(2026, 8, 22), 7);
        Activity activityBeforeWeek = activityOn(LocalDate.of(2026, 8, 16), 100);
        Activity activityAfterWeek = activityOn(LocalDate.of(2026, 8, 24), 100);

        WeeklyProgress progress = calculator.calculate(
                target,
                List.of(activityInsideWeek, anotherActivityInsideWeek, activityBeforeWeek, activityAfterWeek)
        );

        assertThat(progress.actualDistance().inKm()).isEqualTo(15.0);
    }

    @Test
    void calculate_returns_zero_actual_distance_when_no_activity_matches() {
        WeeklyProgress progress = calculator.calculate(target, List.of());

        assertThat(progress.actualDistance()).isEqualTo(Distance.zero());
    }

    @Test
    void calculate_computes_completion_rate_as_actual_over_target() {
        Activity activity = activityOn(LocalDate.of(2026, 8, 18), 15);

        WeeklyProgress progress = calculator.calculate(target, List.of(activity));

        assertThat(progress.completionRate()).isCloseTo(0.75, within(0.0001));
    }

    @Test
    void calculate_derives_the_correct_status_from_completion_rate() {
        Activity activity = activityOn(LocalDate.of(2026, 8, 18), 25); // 125% de l'objectif

        WeeklyProgress progress = calculator.calculate(target, List.of(activity));

        assertThat(progress.status()).isEqualTo(ProgressStatus.EN_AVANCE);
    }

    @Test
    void calculate_returns_a_result_referencing_the_original_target() {
        WeeklyProgress progress = calculator.calculate(target, List.of());

        assertThat(progress.target()).isEqualTo(target);
    }
}