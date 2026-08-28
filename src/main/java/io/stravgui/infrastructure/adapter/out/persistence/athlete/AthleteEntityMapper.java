package io.stravgui.infrastructure.adapter.out.persistence.athlete;

import io.stravgui.domain.athlete.Athlete;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.athlete.OAuthCredentials;
import io.stravgui.infrastructure.adapter.out.security.TokenEncryptionService;
import org.springframework.stereotype.Component;

@Component
class AthleteEntityMapper {

    private final TokenEncryptionService tokenEncryptionService;

    AthleteEntityMapper(TokenEncryptionService tokenEncryptionService) {
        this.tokenEncryptionService = tokenEncryptionService;
    }

    AthleteEntity toEntity(Athlete domain) {
        OAuthCredentials credentials = domain.stravaCredentials();
        return new AthleteEntity(
                domain.id().value(),
                tokenEncryptionService.encrypt(credentials.accessToken()),
                tokenEncryptionService.encrypt(credentials.refreshToken()),
                credentials.expiresAt()
        );
    }

    Athlete toDomain(AthleteEntity entity) {
        OAuthCredentials credentials = new OAuthCredentials(
                tokenEncryptionService.decrypt(entity.getAccessToken()),
                tokenEncryptionService.decrypt(entity.getRefreshToken()),
                entity.getExpiresAt()
        );
        return Athlete.reconstitute(new AthleteId(entity.getId()), credentials);
    }
}