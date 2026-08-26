package io.stravgui.domain.athlete;

import java.util.Objects;
import java.util.UUID;

public record AthleteId(UUID value) {

    public AthleteId {
        Objects.requireNonNull(value, "AthleteId ne peut pas être null");
    }

    public static AthleteId generate() {
        return new AthleteId(UUID.randomUUID());
    }

    public static AthleteId of(String rawValue) {
        Objects.requireNonNull(rawValue, "rawValue ne peut pas être null");
        try {
            return new AthleteId(UUID.fromString(rawValue));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("AthleteId invalide : " + rawValue, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}