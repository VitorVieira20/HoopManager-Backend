package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.game.GameUpdateRequestDTO;
import com.hoopmanger.api.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public Game getGameById( UUID gameId ) {
        return gameRepository.findGameById( gameId );
    }
    public List<Game> getGamesByTeamId( UUID teamId ) {
        return gameRepository.findGamesByTeamId( teamId );
    }
    public List<Game> getGamesByOwnerId( UUID ownerId ) {
        return gameRepository.findGamesByOwnerId( ownerId );
    }

    public Game createGame( GameRequestDTO gameRequestDTO ) {
        Game game = new Game( );
        game.setTeam_id( gameRequestDTO.team_id( ) );
        game.setHome_team( gameRequestDTO.home_team( ) );
        game.setAway_team( gameRequestDTO.away_team( ) );
        game.setHome_score( gameRequestDTO.home_score( ) );
        game.setAway_score( gameRequestDTO.away_score( ) );
        game.setLocation( gameRequestDTO.location( ) );
        game.setDate( gameRequestDTO.date( ) );
        return gameRepository.save( game );
    }

    public Game updateGame( UUID gameId, GameUpdateRequestDTO gameUpdateRequestDTO ) {
        Game game = gameRepository.findById( gameId ).orElse( null );
        if (game == null) {
            return null;
        }
        game.setHome_team( gameUpdateRequestDTO.home_team( ) );
        game.setAway_team( gameUpdateRequestDTO.away_team( ) );
        game.setHome_score( gameUpdateRequestDTO.home_score( ) );
        game.setAway_score( gameUpdateRequestDTO.away_score( ) );
        game.setLocation( gameUpdateRequestDTO.location( ) );
        game.setDate( gameUpdateRequestDTO.date( ) );
        return gameRepository.save( game );
    }

    public boolean deleteGame( UUID gameId ) {
        if (gameRepository.existsById( gameId ) ) {
            gameRepository.deleteById( gameId );
            return true;
        } else {
            return false;
        }
    }
}
