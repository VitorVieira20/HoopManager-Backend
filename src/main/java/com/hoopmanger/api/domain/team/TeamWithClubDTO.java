package com.hoopmanger.api.domain.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamWithClubDTO {
    private UUID id;
    private String name;
    private UUID clubId;
    private String clubName;
}