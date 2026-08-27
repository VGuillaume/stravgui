package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.domain.activity.ActivityType;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class StravaTypeMapper {

    static ActivityType toDomainType(String stravaType) {
        return switch (stravaType) {
            case "Run" -> ActivityType.RUN;
            case "Ride" -> ActivityType.RIDE;
            case "Swim" -> ActivityType.SWIM;
            case "Walk" -> ActivityType.WALK;
            case "Padel" -> ActivityType.PADEL;
            case "Workout" -> ActivityType.WORKOUT;
            default -> ActivityType.OTHER;
        };
    }

    static LocalDate toLocalDate(String isoDateTime) {
        return OffsetDateTime.parse(isoDateTime).toLocalDate();
    }
}