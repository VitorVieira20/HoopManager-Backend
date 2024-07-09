package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.domain.team.TeamUpdateRequestDTO;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.TeamRepository;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    public TeamWithClubDTO getTeamById( UUID teamId ) {
        return teamRepository.findTeamById( teamId );
    }
    public List<Team> getTeamsByClubId( UUID clubId ) {
        return teamRepository.findTeamsByClubId( clubId );
    }
    public List<Team> getTeamsByOwnerId( UUID ownerId ) {
        return teamRepository.findTeamsByOwnerId( ownerId );
    }
    public List<TeamWithClubDTO> getTeamsByName( String teamName ) {
        return teamRepository.findTeamsByName( teamName );
    }
    public List<TeamWithClubDTO> getUserFavoriteTeamsByUserId( UUID userId ) {
        User user = userRepository.findById( userId ).orElse( null );
        if ( user != null && user.getTeams( ) != null ) {
            return teamRepository.findUserFavoriteTeamsByIds( user.getTeams( ) );
        } else {
            return List.of( );
        }
    }

    public Team createTeam(TeamRequestDTO teamRequestDTO ) {
        Team team = new Team ( );
        team.setClub_id( teamRequestDTO.club_id( ) );
        team.setName( teamRequestDTO.name( ) );
        return teamRepository.save( team );
    }

    public Team updateTeam( UUID teamId, TeamUpdateRequestDTO teamUpdateRequestDTO ) {
        Team team = teamRepository.findById( teamId ).orElse( null );
        if ( teamId == null ) {
            return null;
        }
        team.setName( teamUpdateRequestDTO.name( ) );
        return teamRepository.save( team );
    }

    public boolean deleteTeam( UUID teamId) {
        if (teamRepository.existsById( teamId ) ) {
            teamRepository.deleteById( teamId );
            return true;
        } else {
            return false;
        }
    }
}
