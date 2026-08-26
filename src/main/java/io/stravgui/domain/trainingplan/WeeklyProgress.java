package io.stravgui.domain.trainingplan;

import io.stravgui.domain.shared.Distance;
import java.util.Objects;

public record WeeklyProgress(
        WeeklyTarget target,
        Distance actualDistance,
        double completionRate,
        ProgressStatus status
) {
    public WeeklyProgress {
        Objects.requireNonNull(target, "target ne peut pas être null");
        Objects.requireNonNull(actualDistance, "actualDistance ne peut pas être null");
        Objects.requireNonNull(status, "status ne peut pas être null");
    }
}