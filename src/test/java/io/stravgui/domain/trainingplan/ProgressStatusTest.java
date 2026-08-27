package io.stravgui.domain.trainingplan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProgressStatusTest {

    @ParameterizedTest
    @CsvSource({
            "1.5,  EN_AVANCE",      // largement au-dessus du seuil
            "1.1,  EN_AVANCE",      // exactement au seuil haut (borne incluse)
            "1.05, SUR_OBJECTIF",   // juste en dessous du seuil haut
            "0.9,  SUR_OBJECTIF",   // exactement au seuil bas (borne incluse)
            "0.89, EN_RETARD",      // juste en dessous du seuil bas
            "0.0,  EN_RETARD"       // aucune activité réalisée
    })
    void from_maps_completion_rate_to_the_correct_status(double completionRate, ProgressStatus expected) {
        assertThat(ProgressStatus.from(completionRate)).isEqualTo(expected);
    }
}