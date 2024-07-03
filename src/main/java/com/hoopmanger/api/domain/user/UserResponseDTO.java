package com.hoopmanger.api.domain.user;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String role
) {
}
