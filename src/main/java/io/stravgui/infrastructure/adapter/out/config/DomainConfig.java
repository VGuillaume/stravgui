package io.stravgui.infrastructure.adapter.out.config;

import io.stravgui.domain.trainingplan.WeeklyProgressCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Certains Bean ici concernent des éléments présents dans la partie "domain".
 * L'objectif ici est de laisser la partie "domain" vierge de tout élément Spring.
 */
@Configuration
public class DomainConfig {

    @Bean
    public WeeklyProgressCalculator weeklyProgressCalculator() {
        return new WeeklyProgressCalculator();
    }
}