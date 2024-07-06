package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.game.GameUpdateRequestDTO;
import com.hoopmanger.api.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/game" )
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping( "/{gameId}" )
    public ResponseEntity<Game> getGameById( @PathVariable UUID gameId ) {
        Game game = gameService.getGameById( gameId );
        if ( game == null ) {
            return  ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( game );
        }
    }

    @GetMapping( "/team/{teamId}" )
    public ResponseEntity<List<Game>> getGamesByTeamId( @PathVariable UUID teamId ) {
        List<Game> games = gameService.getGamesByTeamId( teamId );
        if ( games.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( games );
        }
    }

    @GetMapping( "/owner/{ownerId}" )
    public ResponseEntity<List<Game>> getGamesByOwnerId( @PathVariable UUID ownerId ) {
        List<Game> games = gameService.getGamesByOwnerId( ownerId );
        if ( games.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( games );
        }
    }

    @PostMapping( "/" )
    public ResponseEntity<Game> createGame( @Valid @RequestBody GameRequestDTO gameRequestDTO ) {
        Game createdGame = gameService.createGame( gameRequestDTO );
        return ResponseEntity.ok( createdGame );
    }

    @PutMapping( "/{gameId}" )
    public ResponseEntity<Game> updateGame( @PathVariable UUID gameId, @Valid @RequestBody GameUpdateRequestDTO gameUpdateRequest) {
        Game updatedGame = gameService.updateGame( gameId, gameUpdateRequest );
        if ( updatedGame == null ) {
            return ResponseEntity.notFound( ).build( );
        } else {
            return ResponseEntity.ok( updatedGame );
        }
    }

    @DeleteMapping( "/{gameId}" )
    public ResponseEntity<Void> deleteGame( @PathVariable UUID gameId) {
        boolean deleted = gameService.deleteGame( gameId );
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
