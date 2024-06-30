package com.hoopmanger.api.domain.player;

import javax.validation.constraints.NotBlank;

public record PlayerUpdateRequestDTO (
        @NotBlank(message = "Player name is mandatory")
        String name,
        String position
) {
}
