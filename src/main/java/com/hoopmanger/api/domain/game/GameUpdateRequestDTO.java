package com.hoopmanger.api.domain.game;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public record GameUpdateRequestDTO (
        @NotBlank(message = "Game date is mandatory")
        Date date,
        @NotBlank(message = "Game home team name is mandatory")
        String home_team,
        @NotBlank(message = "Game away team name is mandatory")
        String away_team,
        @NotBlank(message = "Game home team score is mandatory")
        int home_score,
        @NotBlank(message = "Game away team score is mandatory")
        int away_score,
        @NotBlank(message = "Game location is mandatory")
        String location
) {
}
