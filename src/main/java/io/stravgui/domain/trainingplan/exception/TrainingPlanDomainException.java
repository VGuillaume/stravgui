package io.stravgui.domain.trainingplan.exception;

public abstract class TrainingPlanDomainException extends RuntimeException {

    protected TrainingPlanDomainException(String message) {
        super(message);
    }
}