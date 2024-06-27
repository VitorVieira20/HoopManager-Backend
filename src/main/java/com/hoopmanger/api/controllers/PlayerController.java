package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
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

    @GetMapping( "/team/{teamId}" )
    public ResponseEntity<List<Player>> getPlayersByTeamId( @PathVariable UUID teamId ) {
        List<Player> players = playerService.getPlayersByTeamId( teamId );
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
}
