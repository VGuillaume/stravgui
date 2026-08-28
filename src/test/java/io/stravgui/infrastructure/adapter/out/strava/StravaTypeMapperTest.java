package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.domain.activity.ActivityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StravaTypeMapperTest {

    @ParameterizedTest
    @CsvSource({
            "Run,        RUN",
            "Ride,       RIDE",
            "Swim,       SWIM",
            "Walk,       WALK",
            "Hike,       OTHER",   // type Strava non géré explicitement
            "Yoga,       OTHER"
    })
    void toDomainType_maps_known_strava_types_and_falls_back_to_other(String stravaType, ActivityType expected) {
        assertThat(StravaTypeMapper.toDomainType(stravaType)).isEqualTo(expected);
    }

    @Test
    void toLocalDate_extracts_the_date_part_from_an_iso_datetime_with_offset() {
        LocalDate result = StravaTypeMapper.toLocalDate("2026-08-19T07:30:00Z");

        assertThat(result).isEqualTo(LocalDate.of(2026, 8, 19));
    }

    @Test
    void toLocalDate_handles_a_non_utc_offset_without_shifting_the_date() {
        // 23h locale un 19 août avec offset +02:00 : reste bien le 19 août, pas un décalage vers le 20
        LocalDate result = StravaTypeMapper.toLocalDate("2026-08-19T23:45:00+02:00");

        assertThat(result).isEqualTo(LocalDate.of(2026, 8, 19));
    }
}