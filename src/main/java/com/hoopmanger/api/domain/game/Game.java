package com.hoopmanger.api.domain.game;

import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table( name= "tb_games" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private UUID id;

    private Date date;

    private String home_team;

    private String away_team;

    private int home_score;

    private int away_score;

    private String location;

    private UUID team_id;
}
