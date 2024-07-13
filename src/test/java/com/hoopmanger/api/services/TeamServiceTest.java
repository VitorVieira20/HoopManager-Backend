package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.domain.team.TeamUpdateRequestDTO;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.TeamRepository;
import com.hoopmanger.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamService teamService;

    private UUID teamId;
    private UUID clubId;
    private UUID ownerId;
    private UUID userId;
    private Team team;
    private TeamWithClubDTO teamWithClubDTO;
    private List<Team> teams;
    private List<TeamWithClubDTO> teamsWithClub;
    private TeamRequestDTO teamRequestDTO;
    private TeamUpdateRequestDTO teamUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamId = UUID.randomUUID();
        clubId = UUID.randomUUID();
        ownerId = UUID.randomUUID();
        userId = UUID.randomUUID();

        team = new Team();
        team.setId(teamId);
        team.setClub_id(clubId);
        team.setName("Team Name");

        teamWithClubDTO = new TeamWithClubDTO(teamId, "Team Name", clubId, "Club Name");

        teams = Collections.singletonList(team);
        teamsWithClub = Collections.singletonList(teamWithClubDTO);

        teamRequestDTO = new TeamRequestDTO(clubId, "New Team Name");

        teamUpdateRequestDTO = new TeamUpdateRequestDTO("Updated Team Name");
    }

    @Test
    @DisplayName("Get Team by ID")
    void testGetTeamById() {
        when(teamRepository.findTeamById(teamId)).thenReturn(teamWithClubDTO);

        TeamWithClubDTO foundTeam = teamService.getTeamById(teamId);

        assertNotNull(foundTeam);
        assertEquals("Team Name", foundTeam.getName());
    }

    @Test
    @DisplayName("Get Teams by Club ID")
    void testGetTeamsByClubId() {
        when(teamRepository.findTeamsByClubId(clubId)).thenReturn(teams);

        List<Team> foundTeams = teamService.getTeamsByClubId(clubId);

        assertFalse(foundTeams.isEmpty());
        assertEquals(1, foundTeams.size());
        assertEquals("Team Name", foundTeams.get(0).getName());
    }

    @Test
    @DisplayName("Get Teams by Owner ID")
    void testGetTeamsByOwnerId() {
        when(teamRepository.findTeamsByOwnerId(ownerId)).thenReturn(teams);

        List<Team> foundTeams = teamService.getTeamsByOwnerId(ownerId);

        assertFalse(foundTeams.isEmpty());
        assertEquals(1, foundTeams.size());
        assertEquals("Team Name", foundTeams.get(0).getName());
    }

    @Test
    @DisplayName("Get Teams by Name")
    void testGetTeamsByName() {
        when(teamRepository.findTeamsByName("Team Name")).thenReturn(teamsWithClub);

        List<TeamWithClubDTO> foundTeams = teamService.getTeamsByName("Team Name");

        assertFalse(foundTeams.isEmpty());
        assertEquals(1, foundTeams.size());
        assertEquals("Team Name", foundTeams.get(0).getName());
    }

    @Test
    @DisplayName("Get User Favorite Teams by User ID")
    void testGetUserFavoriteTeamsByUserId() {
        User user = new User();
        user.setId(userId);
        user.setTeams(Collections.singletonList(teamId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(teamRepository.findUserFavoriteTeamsByIds(user.getTeams())).thenReturn(teamsWithClub);

        List<TeamWithClubDTO> foundTeams = teamService.getUserFavoriteTeamsByUserId(userId);

        assertFalse(foundTeams.isEmpty());
        assertEquals(1, foundTeams.size());
        assertEquals("Team Name", foundTeams.get(0).getName());
    }

    @Test
    @DisplayName("User Not Found for Favorite Teams")
    void testGetUserFavoriteTeamsByUserIdNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        List<TeamWithClubDTO> foundTeams = teamService.getUserFavoriteTeamsByUserId(userId);

        assertTrue(foundTeams.isEmpty());
    }

    @Test
    @DisplayName("Create Team")
    void testCreateTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team createdTeam = teamService.createTeam(teamRequestDTO);

        assertNotNull(createdTeam);
        assertEquals("Team Name", createdTeam.getName());
    }

    @Test
    @DisplayName("Update Team")
    void testUpdateTeam() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team updatedTeam = teamService.updateTeam(teamId, teamUpdateRequestDTO);

        assertNotNull(updatedTeam);
        assertEquals("Updated Team Name", updatedTeam.getName());
    }

    @Test
    @DisplayName("Team Not Found while updating")
    void testUpdateTeamNotFound() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        Team updatedTeam = teamService.updateTeam(teamId, teamUpdateRequestDTO);

        assertNull(updatedTeam);
    }

    @Test
    @DisplayName("Delete Team")
    void testDeleteTeam() {
        when(teamRepository.existsById(teamId)).thenReturn(true);
        doNothing().when(teamRepository).deleteById(teamId);

        boolean deleted = teamService.deleteTeam(teamId);

        assertTrue(deleted);
        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    @DisplayName("Team Not Found while deleting")
    void testDeleteTeamNotFound() {
        when(teamRepository.existsById(teamId)).thenReturn(false);

        boolean deleted = teamService.deleteTeam(teamId);

        assertFalse(deleted);
        verify(teamRepository, never()).deleteById(teamId);
    }
}
