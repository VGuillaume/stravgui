package io.stravgui.domain.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.trainingplan.exception.InvalidRaceDateException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetAfterRaceDateException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetAlreadyExistsException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetNotFoundException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrainingPlan {

    private final TrainingPlanId id;
    private final AthleteId athleteId;
    private final LocalDate raceDate;
    private Map<LocalDate, WeeklyTarget> weeklyTargets = new LinkedHashMap<>();

    public TrainingPlan(TrainingPlanId id, AthleteId athleteId, LocalDate raceDate) {
        this.id = id;
        this.athleteId = athleteId;
        this.raceDate = raceDate;
    }

    public TrainingPlanId getId() {
        return id;
    }

    public AthleteId getAthleteId() {
        return athleteId;
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public List<WeeklyTarget> getWeeklyTargets() {
        return List.copyOf(weeklyTargets.values());
    }

    // Chemin 1 : création — invariants de création appliqués
    public static TrainingPlan create(AthleteId athleteId, LocalDate raceDate) {
        if (raceDate.isBefore(LocalDate.now())) {
            throw new InvalidRaceDateException(raceDate);
        }
        return new TrainingPlan(TrainingPlanId.generate(), athleteId, raceDate);
    }

    // Chemin 2 : reconstruction depuis la persistance — pas de re-validation
    // de "raceDate dans le futur", uniquement les invariants structurels
    public static TrainingPlan reconstitute(TrainingPlanId id, AthleteId athleteId,
                                            LocalDate raceDate, List<WeeklyTarget> existingTargets) {
        TrainingPlan plan = new TrainingPlan(id, athleteId, raceDate);
        existingTargets.forEach(target -> plan.weeklyTargets.put(target.weekStart(), target));
        return plan;
    }

    public void addWeeklyTarget(WeeklyTarget target) {
        if (weeklyTargets.containsKey(target.weekStart())) {
            throw new WeeklyTargetAlreadyExistsException(target.weekStart());
        }
        if (target.weekStart().isAfter(raceDate) || target.weekEnd().isAfter(raceDate)) {
            throw new WeeklyTargetAfterRaceDateException(target.weekStart(), raceDate);
        }
        weeklyTargets.put(target.weekStart(), target);
    }

    public void replaceWeeklyTarget(WeeklyTarget target) {
        if (!weeklyTargets.containsKey(target.weekStart())) {
            throw new WeeklyTargetNotFoundException(target.weekStart());
        }
        weeklyTargets.put(target.weekStart(), target);
    }

    public Optional<WeeklyTarget> targetForWeek(LocalDate weekStart) {
        return Optional.ofNullable(weeklyTargets.get(weekStart));
    }

    public List<WeeklyTarget> allTargets() {
        return List.copyOf(weeklyTargets.values());
    }
}
