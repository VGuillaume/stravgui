package io.stravgui.infrastructure.adapter.out.persistence.activity;

import io.stravgui.domain.activity.Activity;
import io.stravgui.domain.activity.ActivityId;
import io.stravgui.domain.activity.ActivityRepository;
import io.stravgui.domain.athlete.AthleteId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class ActivityRepositoryAdapter implements ActivityRepository {

    private final JpaActivityRepository jpaRepository;
    private final ActivityEntityMapper mapper;

    ActivityRepositoryAdapter(JpaActivityRepository jpaRepository, ActivityEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Activity activity) {
        jpaRepository.save(mapper.toEntity(activity));
    }

    @Override
    public Optional<Activity> findById(ActivityId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Activity> findByAthlete(AthleteId athleteId) {
        return jpaRepository.findByAthleteId(athleteId.value())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByStravaExternalId(String stravaExternalId) {
        return jpaRepository.existsByStravaExternalId(stravaExternalId);
    }
}