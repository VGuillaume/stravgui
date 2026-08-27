package io.stravgui.domain.athlete;

public interface StravaTokenRefresher {
    OAuthCredentials refresh(OAuthCredentials expiredCredentials);
}