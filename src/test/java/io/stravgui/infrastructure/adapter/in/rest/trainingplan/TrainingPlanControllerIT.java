package io.stravgui.infrastructure.adapter.in.rest.trainingplan;

import io.stravgui.domain.athlete.AthleteId;
import io.stravgui.infrastructure.adapter.in.security.JwtService;
import io.stravgui.infrastructure.adapter.out.persistence.PostgresIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TrainingPlanControllerIT extends PostgresIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JsonMapper jsonMapper;

    private AthleteId athleteId;
    private String token;

    @BeforeEach
    void setUp() {
        athleteId = AthleteId.generate();
        token = jwtService.generateToken(athleteId);
    }

    @Test
    void create_without_token_is_rejected_with_401() throws Exception {
        String requestBody = """
            {"raceDate": "2026-12-31"}
            """;

        mockMvc.perform(post("/api/athletes/{id}/training-plan", athleteId.value())
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void create_with_valid_token_returns_201_with_location_header() throws Exception {
        String requestBody = """
            {"raceDate": "2026-12-31"}
            """;

        mockMvc.perform(post("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.trainingPlanId").exists());
    }

    @Test
    void create_with_past_race_date_returns_400_bad_request() throws Exception {
        String requestBody = """
            {"raceDate": "2020-01-01"}
            """;

        mockMvc.perform(post("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_returns_the_previously_created_plan() throws Exception {
        String requestBody = """
            {"raceDate": "2026-12-31"}
            """;

        mockMvc.perform(post("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raceDate").value("2026-12-31"))
                .andExpect(jsonPath("$.weeklyTargets").isArray())
                .andExpect(jsonPath("$.weeklyTargets").isEmpty());
    }

    @Test
    void get_returns_404_problem_detail_when_athlete_has_no_plan() throws Exception {
        mockMvc.perform(get("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Plan d'entraînement introuvable"));
    }

    @Test
    void addWeeklyTarget_twice_on_the_same_week_returns_409_conflict() throws Exception {
        mockMvc.perform(post("/api/athletes/{id}/training-plan", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("""
                    {"raceDate": "2026-12-31"}
                    """))
                .andExpect(status().isCreated());

        String weeklyTargetBody = """
            {"weekStart": "2026-11-02", "targetDistanceKm": 20.0}
            """;

        mockMvc.perform(post("/api/athletes/{id}/training-plan/weeks", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(weeklyTargetBody))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/api/athletes/{id}/training-plan/weeks", athleteId.value())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(weeklyTargetBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Objectif déjà existant"));
    }
}