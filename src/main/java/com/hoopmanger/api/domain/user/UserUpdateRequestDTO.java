package com.hoopmanger.api.domain.user;


import java.util.List;
import java.util.UUID;

public record UserUpdateRequestDTO(
        List<UUID> clubs,
        List<UUID> teams,
        List<UUID> games,
        List<UUID> players
) {
}
