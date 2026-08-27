package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "training_plan")
public class TrainingPlanEntity {

    @Id
    private UUID id;

    @Column(name = "athlete_id", nullable = false)
    private UUID athleteId;

    @Column(name = "race_date", nullable = false)
    private LocalDate raceDate;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WeeklyTargetEntity> weeklyTargets = new ArrayList<>();

    public TrainingPlanEntity(UUID id, UUID athleteId, LocalDate raceDate) {
        this.id = id;
        this.athleteId = athleteId;
        this.raceDate = raceDate;
    }

    public void addWeeklyTarget(WeeklyTargetEntity target) {
        target.setTrainingPlan(this);
        this.weeklyTargets.add(target);
    }
}