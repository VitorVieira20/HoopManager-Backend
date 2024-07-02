package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO;
import com.hoopmanger.api.services.GameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/gameInfo" )
public class GameInfoController {

    @Autowired
    GameInfoService gameInfoService;

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<GameInfoWithPlayerNameResponseDTO>> getGameInfoByGameId( @PathVariable UUID gameId) {
        List<GameInfoWithPlayerNameResponseDTO> gameInfo = gameInfoService.getGameInfoWithPlayerName(gameId);
        if (gameInfo.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(gameInfo);
        }
    }

    @PostMapping( "/" )
    public ResponseEntity<GameInfo> createGameInfo( @Valid @RequestBody GameInfoRequestDTO gameInfoRequestDTO ) {
        GameInfo createdGameInfo = gameInfoService.createGameInfo( gameInfoRequestDTO );
        return ResponseEntity.ok( createdGameInfo );
    }
}
