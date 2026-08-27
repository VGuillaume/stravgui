package io.stravgui.domain.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import io.stravgui.domain.trainingplan.exception.InvalidRaceDateException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetAfterRaceDateException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TrainingPlanTest {

    private final AthleteId athleteId = AthleteId.generate();
    private final LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    @Test
    void create_builds_a_plan_with_no_weekly_target_initially() {
        LocalDate futureRaceDate = LocalDate.now().plusMonths(2);

        TrainingPlan plan = TrainingPlan.create(athleteId, futureRaceDate);

        assertThat(plan.getAthleteId()).isEqualTo(athleteId);
        assertThat(plan.getRaceDate()).isEqualTo(futureRaceDate);
        assertThat(plan.getWeeklyTargets()).isEmpty();
    }

    @Test
    void create_rejects_a_race_date_in_the_past() {
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> TrainingPlan.create(athleteId, pastDate))
                .isInstanceOf(InvalidRaceDateException.class);
    }

    @Test
    void addWeeklyTarget_adds_a_target_successfully() {
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));
        WeeklyTarget target = new WeeklyTarget(nextMonday, Distance.ofKm(20));

        plan.addWeeklyTarget(target);

        assertThat(plan.getWeeklyTargets()).containsExactly(target);
    }

    @Test
    void addWeeklyTarget_rejects_a_duplicate_week_start() {
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));
        plan.addWeeklyTarget(new WeeklyTarget(nextMonday, Distance.ofKm(20)));

        WeeklyTarget duplicate = new WeeklyTarget(nextMonday, Distance.ofKm(25));

        assertThatThrownBy(() -> plan.addWeeklyTarget(duplicate))
                .isInstanceOf(WeeklyTargetAlreadyExistsException.class);
    }

    @Test
    void addWeeklyTarget_rejects_a_week_starting_after_the_race_date() {
        LocalDate raceDate = nextMonday.plusDays(3); // la course tombe avant la fin de la semaine cible
        TrainingPlan plan = TrainingPlan.create(athleteId, raceDate);

        WeeklyTarget targetAfterRace = new WeeklyTarget(nextMonday, Distance.ofKm(20));

        assertThatThrownBy(() -> plan.addWeeklyTarget(targetAfterRace))
                .isInstanceOf(WeeklyTargetAfterRaceDateException.class);
    }

    @Test
    void targetForWeek_returns_the_matching_target_when_present() {
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));
        WeeklyTarget target = new WeeklyTarget(nextMonday, Distance.ofKm(20));
        plan.addWeeklyTarget(target);

        assertThat(plan.targetForWeek(nextMonday)).contains(target);
    }

    @Test
    void targetForWeek_returns_empty_when_no_target_matches() {
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));

        assertThat(plan.targetForWeek(nextMonday)).isEmpty();
    }

    @Test
    void getWeeklyTargets_returns_an_immutable_copy() {
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));
        plan.addWeeklyTarget(new WeeklyTarget(nextMonday, Distance.ofKm(20)));

        var targets = plan.getWeeklyTargets();

        assertThatThrownBy(() -> targets.add(new WeeklyTarget(nextMonday.plusWeeks(1), Distance.ofKm(15))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}