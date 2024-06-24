package com.hoopmanger.api.domain.player;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table( name= "tb_players" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team_id;
}
