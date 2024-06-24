package com.hoopmanger.api.domain.team;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table( name= "tb_teams" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
}
