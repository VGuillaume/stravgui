package io.stravgui.infrastructure.adapter.out.persistence.athlete;

import io.stravgui.domain.athlete.Athlete;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.OAuthCredentials;
import org.springframework.stereotype.Component;

@Component
class AthleteEntityMapper {

    AthleteEntity toEntity(Athlete domain) {
        OAuthCredentials credentials = domain.stravaCredentials();
        return new AthleteEntity(
                domain.id().value(),
                credentials.accessToken(),
                credentials.refreshToken(),
                credentials.expiresAt()
        );
    }

    Athlete toDomain(AthleteEntity entity) {
        OAuthCredentials credentials = new OAuthCredentials(
                entity.getAccessToken(), entity.getRefreshToken(), entity.getExpiresAt()
        );
        return Athlete.reconstitute(new AthleteId(entity.getId()), credentials);
    }
}