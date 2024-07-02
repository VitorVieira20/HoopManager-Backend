package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query( "SELECT p FROM Player p WHERE p.id = :playerId" )
    Player findPlayerById( @Param( "playerId" ) UUID playerId );

    @Query( "SELECT p FROM Player p WHERE p.team_id = :teamId" )
    List<Player> findPlayersByTeamId( @Param( "teamId" ) UUID teamId );

    @Query( "SELECT p FROM Player p INNER JOIN Team t ON p.team_id = t.id INNER JOIN Game g ON t.id = g.team_id WHERE g.id = :gameId " )
    List<Player> findPlayersByGameId( @Param( "gameId" ) UUID gameId );

    @Query( "SELECT p " +
            "FROM Player p " +
            "LEFT JOIN GameInfo g ON p.id = g.player_id AND g.game_id = :gameId " +
            "WHERE g.player_id IS NULL " +
            "AND p.team_id = (SELECT team_id FROM Game WHERE id = :gameId) " )
    List<Player> findRemainingPlayersFromGameInfoByGameId( @Param( "gameId" ) UUID gameId );
}
