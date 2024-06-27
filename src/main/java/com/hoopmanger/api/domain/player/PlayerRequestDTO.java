package com.hoopmanger.api.domain.player;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record PlayerRequestDTO (
        @NotNull(message = "Team ID is mandatory")
        UUID team_id,
        @NotBlank(message = "Player name is mandatory")
        String name,
        String position
) {
}
