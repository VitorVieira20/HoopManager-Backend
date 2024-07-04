package com.hoopmanger.api.domain.user.auth;

public record LoginRequestDTO (
        String email,
        String password
) {
}
