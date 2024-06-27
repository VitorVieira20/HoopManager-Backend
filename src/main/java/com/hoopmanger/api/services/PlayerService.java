package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getPlayersByTeamId( UUID teamId ) {
        return playerRepository.findPlayersByTeamId( teamId );
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

}
