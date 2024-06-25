package com.hoopmanger.api.domain.club;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ClubUpdateRequestDTO (
        @NotBlank(message = "Club name is mandatory")
        String name,
        @NotBlank(message = "Club email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Club phone is mandatory")
        long phone,
        @NotBlank(message = "Club instagram is mandatory")
        String instagram,
        @NotBlank(message = "Club twitter is mandatory")
        String twitter,
        @NotBlank(message = "Club facebook is mandatory")
        String facebook
) {
}
