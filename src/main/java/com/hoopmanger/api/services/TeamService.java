package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.team.Team;
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
}
