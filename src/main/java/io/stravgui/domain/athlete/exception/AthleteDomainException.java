package io.stravgui.domain.athlete.exception;

public abstract class AthleteDomainException extends RuntimeException {

    protected AthleteDomainException(String message) {
        super(message);
    }
}