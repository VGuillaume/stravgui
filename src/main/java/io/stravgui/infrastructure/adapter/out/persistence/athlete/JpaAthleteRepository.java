package io.stravgui.infrastructure.adapter.out.persistence.athlete;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaAthleteRepository extends JpaRepository<AthleteEntity, UUID> {
}