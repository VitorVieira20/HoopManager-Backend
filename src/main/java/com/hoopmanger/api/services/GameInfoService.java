package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO;
import com.hoopmanger.api.repositories.GameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameInfoService {

    @Autowired
    GameInfoRepository gameInfoRepository;

    public List<GameInfoWithPlayerNameResponseDTO> getGameInfoWithPlayerName(UUID gameId) {
        return gameInfoRepository.findGameInfoWithPlayerNameByGameId(gameId);
    }

    public GameInfo createGameInfo(GameInfoRequestDTO gameInfoRequestDTO ) {
        GameInfo gameInfo = new GameInfo( );
        gameInfo.setPlayer_id( gameInfoRequestDTO.player_id( ) );
        gameInfo.setAssists( gameInfoRequestDTO.assists( ) );
        gameInfo.setPoints( gameInfoRequestDTO.points( ) );
        gameInfo.setRebounds( gameInfoRequestDTO.rebounds( ) );
        gameInfo.setGame_id( gameInfoRequestDTO.game_id( ) );
        return gameInfoRepository.save( gameInfo );
    }
}
