package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanId;
import io.stravgui.domain.trainingplan.TrainingPlanRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class TrainingPlanRepositoryAdapter implements TrainingPlanRepository {

    private final JpaTrainingPlanRepository jpaRepository;
    private final TrainingPlanEntityMapper mapper;

    TrainingPlanRepositoryAdapter(JpaTrainingPlanRepository jpaRepository, TrainingPlanEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(TrainingPlan trainingPlan) {
        jpaRepository.save(mapper.toEntity(trainingPlan));
    }

    @Override
    public Optional<TrainingPlan> findById(TrainingPlanId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TrainingPlan> findByAthleteId(AthleteId athleteId) {
        return jpaRepository.findByAthleteId(athleteId.value()).map(mapper::toDomain);
    }
}