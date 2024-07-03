package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfoUpdateRequestDTO;
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

    public GameInfoWithPlayerNameResponseDTO getGameInfoById( Integer gameInfoId ) {
        return gameInfoRepository.findGameInfoById( gameInfoId );
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

    public GameInfo updateGameInfo( Integer gameInfoId, GameInfoUpdateRequestDTO gameInfoUpdateRequestDTO ) {
        GameInfo gameInfo = gameInfoRepository.findById( gameInfoId ).orElse( null );
        if ( gameInfo == null ) {
            return null;
        }
        gameInfo.setPoints( gameInfoUpdateRequestDTO.points( ) );
        gameInfo.setAssists( gameInfoUpdateRequestDTO.assists( ) );
        gameInfo.setRebounds( gameInfoUpdateRequestDTO.rebounds( ) );
        return gameInfoRepository.save( gameInfo );
    }

    public boolean deleteGameInfo( Integer gameInfoId ) {
        if ( gameInfoRepository.existsById( gameInfoId ) ) {
            gameInfoRepository.deleteById( gameInfoId );
            return true;
        } else {
            return false;
        }
    }
}
