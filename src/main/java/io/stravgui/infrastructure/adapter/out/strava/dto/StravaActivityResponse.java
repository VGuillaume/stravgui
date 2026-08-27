package io.stravgui.infrastructure.adapter.out.strava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StravaActivityResponse(
        @JsonProperty("id") long stravaId,
        @JsonProperty("type") String type,
        @JsonProperty("distance") double distanceMeters,
        @JsonProperty("start_date_local") String startDateLocal
) {
}