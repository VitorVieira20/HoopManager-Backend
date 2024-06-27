package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.domain.team.TeamUpdateRequestDTO;
import com.hoopmanger.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/team" )
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping( "/{teamId}" )
    public ResponseEntity<Team> getTeamById( @PathVariable UUID teamId ) {
        Team team = teamService.getTeamById( teamId );
        if ( team == null ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( team );
        }
    }

    @GetMapping( "/club/{clubId}" )
    public ResponseEntity<List<Team>> getTeamsByClubId( @PathVariable UUID clubId ) {
        List<Team> teams = teamService.getTeamsByClubId( clubId );
        if ( teams.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( teams );
        }
    }

    @PostMapping( "/" )
    public ResponseEntity<Team> createTeam( @Valid @RequestBody TeamRequestDTO teamRequestDTO ) {
        Team createdClub = teamService.createTeam( teamRequestDTO );
        return ResponseEntity.ok( createdClub );
    }

    @PutMapping( "/{teamId}" )
    public ResponseEntity<Team> updateTeam( @PathVariable UUID teamId, @Valid @RequestBody TeamUpdateRequestDTO teamUpdateRequestDTO) {
        Team updatedTeam = teamService.updateTeam( teamId, teamUpdateRequestDTO );
        if ( updatedTeam == null ) {
            return ResponseEntity.notFound( ).build( );
        } else {
            return ResponseEntity.ok( updatedTeam );
        }
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam( @PathVariable UUID teamId ) {
        boolean deleted = teamService.deleteTeam( teamId );
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
