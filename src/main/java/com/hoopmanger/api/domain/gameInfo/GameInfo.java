package com.hoopmanger.api.domain.gameInfo;

import com.hoopmanger.api.domain.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name= "tb_game_info" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameInfo {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player_id;

    private int points;

    private int assists;

    private int rebounds;
}
