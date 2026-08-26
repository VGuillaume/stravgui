package io.stravgui.domain.trainingplan;

import java.util.Objects;
import java.util.UUID;

public record TrainingPlanId(UUID value) {

    public TrainingPlanId {
        Objects.requireNonNull(value, "TrainingPlanId ne peut pas être null");
    }

    public static TrainingPlanId generate() {
        return new TrainingPlanId(UUID.randomUUID());
    }

    public static TrainingPlanId of(String rawValue) {
        Objects.requireNonNull(rawValue, "rawValue ne peut pas être null");
        try {
            return new TrainingPlanId(UUID.fromString(rawValue));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("TrainingPlanId invalide : " + rawValue, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}