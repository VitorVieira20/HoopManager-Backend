package com.hoopmanger.api.domain.team;

import javax.validation.constraints.NotBlank;

public record TeamUpdateRequestDTO (
        @NotBlank(message = "Team name is mandatory")
        String name
) {
}
