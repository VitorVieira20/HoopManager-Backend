package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {

    @Query( "SELECT new com.hoopmanger.api.domain.team.TeamWithClubDTO(t.id, t.name, t.club_id, c.name) " +
            "FROM Team t JOIN Club c ON t.club_id = c.id WHERE t.id = :teamId")
    TeamWithClubDTO findTeamById( @Param( "teamId" ) UUID teamId );

    @Query( "SELECT t FROM Team t WHERE t.club_id = :clubId ORDER BY t.name" )
    List<Team> findTeamsByClubId( @Param( "clubId" ) UUID ownerId );

    @Query( "SELECT t FROM Team t INNER JOIN Club c On t.club_id = c.id WHERE c.owner_id = :ownerId" )
    List<Team> findTeamsByOwnerId( @Param( "ownerId" ) UUID ownerId );

    @Query( "SELECT new com.hoopmanger.api.domain.team.TeamWithClubDTO(t.id, t.name, t.club_id, c.name) " +
            "FROM Team t JOIN Club c ON t.club_id = c.id WHERE t.name ILIKE %:teamName%" )
    List<TeamWithClubDTO> findTeamsByName( @Param( "teamName" ) String teamName );

    @Query( "SELECT new com.hoopmanger.api.domain.team.TeamWithClubDTO(t.id, t.name, t.club_id, c.name) " +
            "FROM Team t JOIN Club c ON t.club_id = c.id WHERE t.id IN :teamIds" )
    List<TeamWithClubDTO> findUserFavoriteTeamsByIds( @Param( "teamIds" ) List<UUID> teamIds );
}
