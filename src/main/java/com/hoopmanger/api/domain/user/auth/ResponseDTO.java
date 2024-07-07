package com.hoopmanger.api.domain.user.auth;

import java.util.List;
import java.util.UUID;

public record ResponseDTO (
        String name,
        String email,
        UUID id,
        String role,
        String plan,
        List<UUID> clubs,
        List<UUID> teams,
        List<UUID> games,
        List<UUID> athletes,
        String token
) {
}
