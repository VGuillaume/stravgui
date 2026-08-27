package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanId;
import io.stravgui.domain.trainingplan.WeeklyTarget;
import io.stravgui.infrastructure.adapter.out.persistence.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingPlanRepositoryAdapterIT extends PostgresIntegrationTest {

    @Autowired
    private TrainingPlanRepositoryAdapter repository;

    private final LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    @Test
    void save_then_findById_returns_an_equivalent_plan() {
        TrainingPlan plan = TrainingPlan.create(AthleteId.generate(), LocalDate.now().plusMonths(2));
        TrainingPlanId id = plan.getId();

        repository.save(plan);
        var found = repository.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(id);
        assertThat(found.get().getAthleteId()).isEqualTo(plan.getAthleteId());
        assertThat(found.get().getRaceDate()).isEqualTo(plan.getRaceDate());
    }

    @Test
    void save_then_findByAthleteId_returns_the_plan_with_its_weekly_targets() {
        AthleteId athleteId = AthleteId.generate();
        TrainingPlan plan = TrainingPlan.create(athleteId, LocalDate.now().plusMonths(2));
        plan.addWeeklyTarget(new WeeklyTarget(nextMonday, Distance.ofKm(20)));
        plan.addWeeklyTarget(new WeeklyTarget(nextMonday.plusWeeks(1), Distance.ofKm(25)));

        repository.save(plan);
        var found = repository.findByAthleteId(athleteId);

        assertThat(found).isPresent();
        assertThat(found.get().getWeeklyTargets())
                .extracting(WeeklyTarget::weekStart, t -> t.targetDistance().inKm())
                .containsExactlyInAnyOrder(
                        org.assertj.core.api.Assertions.tuple(nextMonday, 20.0),
                        org.assertj.core.api.Assertions.tuple(nextMonday.plusWeeks(1), 25.0)
                );
    }

    @Test
    void findById_returns_empty_when_no_plan_exists_for_this_id() {
        var found = repository.findById(TrainingPlanId.generate());

        assertThat(found).isEmpty();
    }

    @Test
    void findByAthleteId_returns_empty_when_athlete_has_no_plan() {
        var found = repository.findByAthleteId(AthleteId.generate());

        assertThat(found).isEmpty();
    }
}