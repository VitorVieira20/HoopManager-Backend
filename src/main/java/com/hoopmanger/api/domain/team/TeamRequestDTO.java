package com.hoopmanger.api.domain.team;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record TeamRequestDTO (
        @NotNull(message = "Club ID is mandatory")
        UUID club_id,
        @NotBlank(message = "Team name is mandatory")
        String name
) {
}
