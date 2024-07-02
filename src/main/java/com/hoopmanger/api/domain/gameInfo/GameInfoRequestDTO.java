package com.hoopmanger.api.domain.gameInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record GameInfoRequestDTO (
        @NotNull(message = "Player ID is mandatory")
        UUID player_id,
        int points,
        int assists,
        int rebounds,
        @NotBlank(message = "Game ID is mandatory")
        UUID game_id
) {
}
