package io.stravgui.domain.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DistanceTest {

    @Test
    void ofKm_creates_a_distance_with_the_given_value() {
        Distance distance = Distance.ofKm(10.5);

        assertThat(distance.inKm()).isEqualTo(10.5);
    }

    @Test
    void ofMeters_converts_correctly_to_kilometers() {
        Distance distance = Distance.ofMeters(1500);

        assertThat(distance.inKm()).isEqualTo(1.5);
    }

    @Test
    void zero_creates_a_distance_of_zero_kilometers() {
        assertThat(Distance.zero().inKm()).isZero();
    }

    @Test
    void constructor_rejects_negative_distance() {
        assertThatThrownBy(() -> Distance.ofKm(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("négative");
    }

    @Test
    void add_sums_two_distances() {
        Distance result = Distance.ofKm(5).add(Distance.ofKm(3.2));

        assertThat(result.inKm()).isEqualTo(8.2);
    }

    @Test
    void subtract_returns_the_positive_difference() {
        Distance result = Distance.ofKm(10).subtract(Distance.ofKm(4));

        assertThat(result.inKm()).isEqualTo(6);
    }

    @Test
    void subtract_rejects_a_result_that_would_be_meaningfully_negative() {
        assertThatThrownBy(() -> Distance.ofKm(3).subtract(Distance.ofKm(10)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 10.0, true",   // égalité stricte
            "10.5, 10.0, true",   // strictement supérieur
            "9.9,  10.0, false"   // strictement inférieur
    })
    void isGreaterOrEqualTo_compares_distances_with_epsilon_tolerance(double a, double b, boolean expected) {
        boolean result = Distance.ofKm(a).isGreaterOrEqualTo(Distance.ofKm(b));

        assertThat(result).isEqualTo(expected);
    }
}