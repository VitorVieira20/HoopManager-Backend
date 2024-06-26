package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getTeamsByClubId( UUID clubId ) {
        return teamRepository.findTeamsByClubId( clubId );
    }

    public Team createTeam(TeamRequestDTO teamRequestDTO ) {
        Team team = new Team ( );
        team.setClub_id( teamRequestDTO.club_id( ) );
        team.setName( teamRequestDTO.name( ) );
        return teamRepository.save( team );
    }
}
