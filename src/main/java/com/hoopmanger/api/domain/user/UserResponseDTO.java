package com.hoopmanger.api.domain.user;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String role,
        String plan,
        List<UUID> clubs,
        List<UUID> teams,
        List<UUID> games,
        List<UUID> athletes
) {
}
