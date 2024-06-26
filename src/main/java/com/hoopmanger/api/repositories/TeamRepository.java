package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Query( "SELECT t FROM Team t WHERE t.club_id = :clubId" )
    List<Team> findTeamsByClubId( @Param( "clubId" ) UUID ownerId );
}
