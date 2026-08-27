package io.stravgui.infrastructure.adapter.out.strava;

import io.stravgui.infrastructure.adapter.out.strava.dto.StravaActivityResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface StravaActivityClient {

    @GetExchange("/athlete/activities")
    List<StravaActivityResponse> getActivities(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("page") int page,
            @RequestParam("per_page") int perPage
    );
}