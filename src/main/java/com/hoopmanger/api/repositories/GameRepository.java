package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query( "SELECT g FROM Game g WHERE g.id = :gameId" )
    Game findGameById( @Param( "gameId" ) UUID gameId );

    @Query( "SELECT g FROM Game g WHERE g.team_id = :teamId ORDER BY g.date" )
    List<Game> findGamesByTeamId( @Param( "teamId" ) UUID teamId );

    @Query( "SELECT g FROM Game g INNER JOIN Team t ON g.team_id = t.id INNER JOIN Club c ON t.club_id = c.id WHERE c.owner_id = :ownerId" )
    List<Game> findGamesByOwnerId( @Param( "ownerId" ) UUID ownerId );
}
