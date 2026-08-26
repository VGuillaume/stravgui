package io.stravgui.infrastructure.adapter.out.persistence.trainingplan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "weekly_target", uniqueConstraints = @UniqueConstraint(columnNames = {"training_plan_id", "week_start"}))
public class WeeklyTargetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlanEntity trainingPlan;

    @Column(name = "week_start", nullable = false)
    private LocalDate weekStart;

    @Column(name = "target_distance_km", nullable = false)
    private double targetDistanceKm;

    public WeeklyTargetEntity(LocalDate weekStart, double targetDistanceKm) {
        this.weekStart = weekStart;
        this.targetDistanceKm = targetDistanceKm;
    }

    void setTrainingPlan(TrainingPlanEntity trainingPlan) {
        this.trainingPlan = trainingPlan;
    }
}