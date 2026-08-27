package io.stravgui.infrastructure.adapter.out.persistence.athlete;

import io.stravgui.domain.athlete.Athlete;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.AthleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class AthleteRepositoryAdapter implements AthleteRepository {

    private final JpaAthleteRepository jpaRepository;
    private final AthleteEntityMapper mapper;

    AthleteRepositoryAdapter(JpaAthleteRepository jpaRepository, AthleteEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Athlete athlete) {
        jpaRepository.save(mapper.toEntity(athlete));
    }

    @Override
    public Optional<Athlete> findById(AthleteId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }
}