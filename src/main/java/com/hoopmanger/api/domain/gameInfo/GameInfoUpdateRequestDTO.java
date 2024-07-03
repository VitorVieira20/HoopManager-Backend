package com.hoopmanger.api.domain.gameInfo;

public record GameInfoUpdateRequestDTO (
        int points,
        int assists,
        int rebounds
) {
}
