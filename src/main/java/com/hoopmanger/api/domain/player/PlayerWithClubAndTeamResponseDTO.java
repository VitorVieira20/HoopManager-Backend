package com.hoopmanger.api.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerWithClubAndTeamResponseDTO {
    private UUID id;
    private String name;
    private String position;
    private UUID team_id;
    private UUID club_id;
    private String teamName;
    private String clubName;
}
