package io.stravgui.infrastructure.adapter.in.rest.trainingplan;

import io.stravgui.application.trainingplan.AddWeeklyTargetUseCase;
import io.stravgui.application.trainingplan.CreateTrainingPlanUseCase;
import io.stravgui.application.trainingplan.GetTrainingPlanUseCase;
import io.stravgui.application.trainingplan.GetWeeklyProgressUseCase;
import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.domain.shared.Distance;
import io.stravgui.domain.trainingplan.TrainingPlan;
import io.stravgui.domain.trainingplan.TrainingPlanId;
import io.stravgui.domain.trainingplan.WeeklyProgress;
import io.stravgui.infrastructure.adapter.in.rest.trainingplan.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/athletes/{athleteId}/training-plan")
@Tag(name = "Training Plan", description = "Gestion du plan d'entraînement personnel")
public class TrainingPlanController {

    private final CreateTrainingPlanUseCase createTrainingPlanUseCase;
    private final AddWeeklyTargetUseCase addWeeklyTargetUseCase;
    private final GetTrainingPlanUseCase getTrainingPlanUseCase;
    private final GetWeeklyProgressUseCase getWeeklyProgressUseCase;
    private final TrainingPlanDtoMapper mapper;

    public TrainingPlanController(CreateTrainingPlanUseCase createTrainingPlanUseCase,
                                  AddWeeklyTargetUseCase addWeeklyTargetUseCase,
                                  GetTrainingPlanUseCase getTrainingPlanUseCase,
                                  GetWeeklyProgressUseCase getWeeklyProgressUseCase,
                                  TrainingPlanDtoMapper mapper) {
        this.createTrainingPlanUseCase = createTrainingPlanUseCase;
        this.addWeeklyTargetUseCase = addWeeklyTargetUseCase;
        this.getTrainingPlanUseCase = getTrainingPlanUseCase;
        this.getWeeklyProgressUseCase = getWeeklyProgressUseCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Créer un plan d'entraînement pour une course cible")
    @ApiResponse(responseCode = "201", description = "Plan créé")
    @PostMapping
    public ResponseEntity<CreateTrainingPlanResponse> create(
            @PathVariable AthleteId athleteId,
            @RequestBody @jakarta.validation.Valid CreateTrainingPlanRequest request) {

        TrainingPlanId id = createTrainingPlanUseCase.execute(athleteId, request.raceDate());

        URI location = URI.create("/api/athletes/%s/training-plan".formatted(athleteId.value()));
        return ResponseEntity.created(location).body(new CreateTrainingPlanResponse(id.value()));
    }

    @Operation(summary = "Ajouter un objectif de distance pour une semaine donnée")
    @ApiResponse(responseCode = "204", description = "Objectif ajouté")
    @PostMapping("/weeks")
    public ResponseEntity<Void> addWeeklyTarget(
            @PathVariable AthleteId athleteId,
            @RequestBody @jakarta.validation.Valid AddWeeklyTargetRequest request) {

        addWeeklyTargetUseCase.execute(athleteId, request.weekStart(), Distance.ofKm(request.targetDistanceKm()));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Consulter le plan d'entraînement complet")
    @GetMapping
    public TrainingPlanResponse get(@PathVariable AthleteId athleteId) {
        TrainingPlan plan = getTrainingPlanUseCase.execute(athleteId);
        return mapper.toResponse(plan);
    }

    @Operation(summary = "Consulter la progression d'une semaine donnée")
    @GetMapping("/progress/{weekStart}")
    public WeeklyProgressResponse getProgress(
            @PathVariable AthleteId athleteId,
            @PathVariable @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate weekStart) {

        WeeklyProgress progress = getWeeklyProgressUseCase.execute(athleteId, weekStart);
        return mapper.toResponse(progress);
    }
}