package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
import com.hoopmanger.api.domain.player.PlayerUpdateRequestDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamUpdateRequestDTO;
import com.hoopmanger.api.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/player" )
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping( "/{playerId}" )
    public ResponseEntity<Player> getPlayerById( @PathVariable UUID playerId ) {
        Player player = playerService.getPlayerById( playerId );
        if ( player == null ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( player );
        }
    }

    @GetMapping( "/team/{teamId}" )
    public ResponseEntity<List<Player>> getPlayersByTeamId( @PathVariable UUID teamId ) {
        List<Player> players = playerService.getPlayersByTeamId( teamId );
        if ( players.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( players );
        }
    }

    @GetMapping( "/game/{gameId}" )
    public ResponseEntity<List<Player>> getPlayersByGameId( @PathVariable UUID gameId ) {
        List<Player> players = playerService.getPlayersByGameId( gameId );
        if ( players.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( players );
        }
    }

    @GetMapping( "/gameInfo/{gameId}" )
    public ResponseEntity<List<Player>> getRemainingPlayersFromGameInfoByGameId( @PathVariable UUID gameId ) {
        List<Player> players = playerService.getRemainingPlayersFromGameInfoByGameId( gameId );
        if ( players.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( players );
        }
    }

    @PostMapping( "/" )
    public ResponseEntity<Player> createPlayer( @Valid @RequestBody PlayerRequestDTO playerRequestDTO ) {
        Player createdPlayer = playerService.createPlayer( playerRequestDTO );
        return ResponseEntity.ok( createdPlayer );
    }

    @PutMapping( "/{playerId}" )
    public ResponseEntity<Player> updatePlayer( @PathVariable UUID playerId, @Valid @RequestBody PlayerUpdateRequestDTO playerUpdateRequestDTO) {
        Player updatedPlayer = playerService.updatePlayer( playerId, playerUpdateRequestDTO );
        if ( updatedPlayer == null ) {
            return ResponseEntity.notFound( ).build( );
        } else {
            return ResponseEntity.ok( updatedPlayer );
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer( @PathVariable UUID playerId ) {
        boolean deleted = playerService.deletePlayer( playerId );
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
