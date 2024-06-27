package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query( "SELECT p FROM Player p WHERE p.team_id = :teamId" )
    List<Player> findPlayersByTeamId(@Param( "teamId" ) UUID teamId );
}
