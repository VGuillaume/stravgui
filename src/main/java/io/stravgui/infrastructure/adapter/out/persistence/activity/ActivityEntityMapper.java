package io.stravgui.infrastructure.adapter.out.persistence.activity;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityId;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import org.springframework.stereotype.Component;

@Component
class ActivityEntityMapper {

    ActivityEntity toEntity(Activity domain) {
        return new ActivityEntity(
                domain.id().value(),
                domain.athleteId().value(),
                domain.stravaExternalId(),
                domain.type(),
                domain.distance().inKm(),
                domain.startDate()
        );
    }

    Activity toDomain(ActivityEntity entity) {
        return Activity.reconstitute(
                new ActivityId(entity.getId()),
                new AthleteId(entity.getAthleteId()),
                entity.getStravaExternalId(),
                entity.getType(),
                Distance.ofKm(entity.getDistanceKm()),
                entity.getStartDate()
        );
    }
}