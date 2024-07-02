package com.hoopmanger.api.domain.gameInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoWithPlayerNameResponseDTO {
    private Integer id;
    private UUID player_id;
    private int points;
    private int assists;
    private int rebounds;
    private UUID game_id;
    private String player_name;
}
