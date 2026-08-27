package io.stravgui.infrastructure.adapter.in.rest.activity;

import io.stravgui.application.activity.SyncAthleteActivitiesUseCase;
import io.stravgui.domain.athlete.AthleteId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/athletes/{athleteId}/activities")
@Tag(name = "Activities", description = "Synchronisation des activités Strava")
public class ActivityController {

    private final SyncAthleteActivitiesUseCase syncAthleteActivitiesUseCase;

    public ActivityController(SyncAthleteActivitiesUseCase syncAthleteActivitiesUseCase) {
        this.syncAthleteActivitiesUseCase = syncAthleteActivitiesUseCase;
    }

    @Operation(summary = "Synchronise les activités récentes depuis Strava")
    @PostMapping("/sync")
    public SyncResult sync(@PathVariable AthleteId athleteId) {
        int imported = syncAthleteActivitiesUseCase.execute(athleteId);
        return new SyncResult(imported);
    }

    public record SyncResult(int importedCount) {
    }
}