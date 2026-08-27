package io.stravgui.application.athlete;

import io.stravgui.domain.athlete.Athlete;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.AthleteRepository;
import io.stravgui.domain.athlete.OAuthCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectAthleteToStravaUseCase {

    private final AthleteRepository athleteRepository;

    public ConnectAthleteToStravaUseCase(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Transactional
    public AthleteId execute(OAuthCredentials credentials) {
        Athlete athlete = Athlete.connectToStrava(credentials);
        athleteRepository.save(athlete);
        return athlete.id();
    }
}