package io.stravgui.domain.trainingplan;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.shared.Distance;

import java.util.List;

public class WeeklyProgressCalculator {

    public WeeklyProgress calculate(WeeklyTarget target, List<Activity> allAthleteActivities) {
        Distance actualDistance = allAthleteActivities.stream()
                .filter(activity -> activity.isWithin(target.weekStart(), target.weekEnd()))
                .map(Activity::distance)
                .reduce(Distance.zero(), Distance::add);

        double completionRate = actualDistance.inKm() / target.targetDistance().inKm();
        ProgressStatus status = ProgressStatus.from(completionRate);

        return new WeeklyProgress(target, actualDistance, completionRate, status);
    }
}