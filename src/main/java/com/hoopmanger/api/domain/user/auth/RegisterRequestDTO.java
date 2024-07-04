package com.hoopmanger.api.domain.user.auth;

public record RegisterRequestDTO (
        String name,
        String email,
        String password
) {
}
