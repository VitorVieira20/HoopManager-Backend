package com.hoopmanger.api.domain.gameInfo;

import com.hoopmanger.api.domain.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table( name= "tb_game_info" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID player_id;

    private int points;

    private int assists;

    private int rebounds;

    private UUID game_id;
}
