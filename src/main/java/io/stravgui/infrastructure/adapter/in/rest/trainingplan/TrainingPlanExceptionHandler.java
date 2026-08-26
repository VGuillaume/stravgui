package io.stravgui.infrastructure.adapter.in.rest.trainingplan;

import io.stravgui.domain.trainingplan.exception.TrainingPlanDomainException;
import io.stravgui.domain.trainingplan.exception.TrainingPlanNotFoundException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetAlreadyExistsException;
import io.stravgui.domain.trainingplan.exception.WeeklyTargetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "io.stravgui.adapter.in.rest.trainingplan")
public class TrainingPlanExceptionHandler {

    @ExceptionHandler(TrainingPlanNotFoundException.class)
    public ProblemDetail handleTrainingPlanNotFound(TrainingPlanNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Plan d'entraînement introuvable");
        return problem;
    }

    @ExceptionHandler(WeeklyTargetAlreadyExistsException.class)
    public ProblemDetail handleAlreadyExists(WeeklyTargetAlreadyExistsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Objectif déjà existant");
        problem.setProperty("errorCode", "WEEKLY_TARGET_ALREADY_EXISTS");
        problem.setProperty("weekStart", ex.weekStart().toString());
        return problem;
    }

    @ExceptionHandler(WeeklyTargetNotFoundException.class)
    public ProblemDetail handleNotFound(WeeklyTargetNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Objectif introuvable");
        problem.setProperty("errorCode", "WEEKLY_TARGET_NOT_FOUND");
        return problem;
    }

    @ExceptionHandler(TrainingPlanDomainException.class)
    public ProblemDetail handleGeneric(TrainingPlanDomainException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Erreur du plan d'entraînement");
        return problem;
    }
}