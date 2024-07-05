package com.hoopmanger.api.domain.user.auth;

import java.util.UUID;

public record ResponseDTO (
        String name,
        String email,
        UUID id,
        String token
) {
}
