package io.stravgui.domain.activity;

import java.util.Objects;
import java.util.UUID;

public record ActivityId(UUID value) {
    public ActivityId {
        Objects.requireNonNull(value, "ActivityId ne peut pas être null");
    }
    public static ActivityId generate() {
        return new ActivityId(UUID.randomUUID());
    }
}