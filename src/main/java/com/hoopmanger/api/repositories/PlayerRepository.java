package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query( "SELECT new com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO(p.id, p.name, p.position, p.team_id, c.id, t.name, c.name) " +
            "FROM Player p JOIN Team t ON p.team_id = t.id JOIN Club c ON t.club_id = c.id WHERE p.id = :playerId" )
    PlayerWithClubAndTeamResponseDTO findPlayerById( @Param( "playerId" ) UUID playerId );

    @Query( "SELECT p FROM Player p WHERE p.team_id = :teamId ORDER BY p.name" )
    List<Player> findPlayersByTeamId( @Param( "teamId" ) UUID teamId );

    @Query( "SELECT p FROM Player p INNER JOIN Team t ON p.team_id = t.id INNER JOIN Game g ON t.id = g.team_id WHERE g.id = :gameId " )
    List<Player> findPlayersByGameId( @Param( "gameId" ) UUID gameId );

    @Query( "SELECT p " +
            "FROM Player p " +
            "LEFT JOIN GameInfo g ON p.id = g.player_id AND g.game_id = :gameId " +
            "WHERE g.player_id IS NULL " +
            "AND p.team_id = (SELECT team_id FROM Game WHERE id = :gameId) " )
    List<Player> findRemainingPlayersFromGameInfoByGameId( @Param( "gameId" ) UUID gameId );

    @Query( "SELECT p FROM Player p INNER JOIN Team t ON p.team_id = t.id INNER JOIN Club c ON t.club_id = c.id WHERE c.owner_id = :ownerId" )
    List<Player> findPlayersByOwnerId( @Param( "ownerId" ) UUID ownerId );

    @Query( "SELECT new com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO(p.id, p.name, p.position, p.team_id, c.id, t.name, c.name) " +
            "FROM Player p JOIN Team t ON p.team_id = t.id JOIN Club c ON t.club_id = c.id WHERE p.name ILIKE %:playerName%" )
    List<PlayerWithClubAndTeamResponseDTO> findPlayersByName( @Param( "playerName" ) String playerName );

    @Query( "SELECT new com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO(p.id, p.name, p.position, p.team_id, c.id, t.name, c.name) " +
            "FROM Player p JOIN Team t ON p.team_id = t.id JOIN Club c ON t.club_id = c.id WHERE p.id IN :playerIds" )
    List<PlayerWithClubAndTeamResponseDTO> findUserFavoritePlayersByIds( @Param( "playerIds" ) List<UUID> playerIds );
}
