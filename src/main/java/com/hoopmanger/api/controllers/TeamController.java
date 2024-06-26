package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/team" )
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping( "/club/{clubId}" )
    public ResponseEntity<List<Team>> getTeamsByClubId( @PathVariable UUID clubId ) {
        List<Team> teams = teamService.getTeamsByClubId( clubId );
        if ( teams.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( teams );
        }
    }
}
