package io.stravgui.domain.trainingplan;

import io.stravgui.domain.shared.Distance;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record WeeklyTarget(LocalDate weekStart, Distance targetDistance) {
    public WeeklyTarget {
        if (weekStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new IllegalArgumentException("weekStart doit être un lundi");
        }
    }
    public LocalDate weekEnd() {
        return weekStart.plusDays(6);
    }
}
