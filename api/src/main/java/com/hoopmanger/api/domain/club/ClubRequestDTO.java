package com.hoopmanger.api.domain.club;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.UUID;

public record ClubRequestDTO(
        @NotNull(message = "Owner ID is mandatory")
        UUID owner_id,
        @NotBlank(message = "Club name is mandatory")
        String name,
        @NotBlank(message = "Club email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        long phone,
        String instagram,
        String twitter,
        String facebook
) {
}
