package io.stravgui.domain.athlete;

import java.util.Optional;

public interface AthleteRepository {
    void save(Athlete athlete);
    Optional<Athlete> findById(AthleteId id);
}