package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
import com.hoopmanger.api.domain.player.PlayerUpdateRequestDTO;
import com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.PlayerRepository;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserRepository userRepository;

    public PlayerWithClubAndTeamResponseDTO getPlayerById( UUID playerId ) {
        return playerRepository.findPlayerById( playerId );
    }
    public List<Player> getPlayersByTeamId( UUID teamId ) {
        return playerRepository.findPlayersByTeamId( teamId );
    }
    public List<Player> getPlayersByGameId( UUID gameId ) {
        return playerRepository.findPlayersByGameId( gameId );
    }
    public List<Player> getRemainingPlayersFromGameInfoByGameId( UUID gameId ) {
        return playerRepository.findRemainingPlayersFromGameInfoByGameId( gameId );
    }
    public List<Player> getPlayersByOwnerId( UUID ownerId ) {
        return playerRepository.findPlayersByOwnerId( ownerId );
    }
    public List<PlayerWithClubAndTeamResponseDTO> getPlayersByName( String playerName ) {
        return playerRepository.findPlayersByName( playerName );
    }
    public List<PlayerWithClubAndTeamResponseDTO> getUserFavoritePlayersByUserId( UUID userId ) {
        User user = userRepository.findById( userId ).orElse( null );
        if ( user != null && user.getAthletes( ) != null ) {
            return playerRepository.findUserFavoritePlayersByIds( user.getAthletes( ) );
        } else {
            return List.of( );
        }
    }

    public Player createPlayer( PlayerRequestDTO playerRequestDTO ) {
        Player player = new Player( );
        player.setTeam_id( playerRequestDTO.team_id( ) );
        player.setName( playerRequestDTO.name( ) );
        if ( ! ( playerRequestDTO.position( ) == null ) ) {
            player.setPosition( playerRequestDTO.position( ) );
        }
        return playerRepository.save( player );
    }

    public Player updatePlayer( UUID playerId, PlayerUpdateRequestDTO playerUpdateRequestDTO ) {
        Player player = playerRepository.findById( playerId ).orElse( null );
        if ( playerId == null ) {
            return null;
        }
        player.setName( playerUpdateRequestDTO.name( ) );
        player.setPosition( playerUpdateRequestDTO.position( ) );
        return playerRepository.save( player );
    }

    public boolean deletePlayer( UUID playerId ) {
        if (playerRepository.existsById( playerId ) ) {
            playerRepository.deleteById( playerId );
            return true;
        } else {
            return false;
        }
    }

}
