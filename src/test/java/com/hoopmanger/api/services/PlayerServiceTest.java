package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
import com.hoopmanger.api.domain.player.PlayerUpdateRequestDTO;
import com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.PlayerRepository;
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
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlayerService playerService;

    private UUID playerId;
    private UUID teamId;
    private UUID userId;
    private Player player;
    private PlayerWithClubAndTeamResponseDTO playerWithClubAndTeam;
    private List<Player> players;
    private List<PlayerWithClubAndTeamResponseDTO> playersWithClubAndTeam;
    private PlayerRequestDTO playerRequestDTO;
    private PlayerUpdateRequestDTO playerUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        userId = UUID.randomUUID();

        player = new Player();
        player.setId(playerId);
        player.setTeam_id(teamId);
        player.setName("Player Name");

        playerWithClubAndTeam = new PlayerWithClubAndTeamResponseDTO(playerId, "Player Name", "Forward", teamId, UUID.randomUUID(), "Team Name", "Club Name");

        players = Collections.singletonList(player);
        playersWithClubAndTeam = Collections.singletonList(playerWithClubAndTeam);

        playerRequestDTO = new PlayerRequestDTO(teamId, "New Player Name", "Guard");

        playerUpdateRequestDTO = new PlayerUpdateRequestDTO("Updated Player Name", "Center");
    }

    @Test
    @DisplayName("Get Player by ID")
    void testGetPlayerById() {
        when(playerRepository.findPlayerById(playerId)).thenReturn(playerWithClubAndTeam);

        PlayerWithClubAndTeamResponseDTO foundPlayer = playerService.getPlayerById(playerId);

        assertNotNull(foundPlayer);
        assertEquals("Player Name", foundPlayer.getName());
    }

    @Test
    @DisplayName("Get Players by Team ID")
    void testGetPlayersByTeamId() {
        when(playerRepository.findPlayersByTeamId(teamId)).thenReturn(players);

        List<Player> foundPlayers = playerService.getPlayersByTeamId(teamId);

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("Get Players by Game ID")
    void testGetPlayersByGameId() {
        UUID gameId = UUID.randomUUID();
        when(playerRepository.findPlayersByGameId(gameId)).thenReturn(players);

        List<Player> foundPlayers = playerService.getPlayersByGameId(gameId);

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("Get Remaining Players from Game Info by Game ID")
    void testGetRemainingPlayersFromGameInfoByGameId() {
        UUID gameId = UUID.randomUUID();
        when(playerRepository.findRemainingPlayersFromGameInfoByGameId(gameId)).thenReturn(players);

        List<Player> foundPlayers = playerService.getRemainingPlayersFromGameInfoByGameId(gameId);

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("Get Players by Owner ID")
    void testGetPlayersByOwnerId() {
        when(playerRepository.findPlayersByOwnerId(userId)).thenReturn(players);

        List<Player> foundPlayers = playerService.getPlayersByOwnerId(userId);

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("Get Players by Name")
    void testGetPlayersByName() {
        when(playerRepository.findPlayersByName("Player Name")).thenReturn(playersWithClubAndTeam);

        List<PlayerWithClubAndTeamResponseDTO> foundPlayers = playerService.getPlayersByName("Player Name");

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("Get User Favorite Players by User ID")
    void testGetUserFavoritePlayersByUserId() {
        User user = new User();
        user.setId(userId);
        user.setAthletes(Collections.singletonList(playerId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(playerRepository.findUserFavoritePlayersByIds(user.getAthletes())).thenReturn(playersWithClubAndTeam);

        List<PlayerWithClubAndTeamResponseDTO> foundPlayers = playerService.getUserFavoritePlayersByUserId(userId);

        assertFalse(foundPlayers.isEmpty());
        assertEquals(1, foundPlayers.size());
        assertEquals("Player Name", foundPlayers.get(0).getName());
    }

    @Test
    @DisplayName("User Not Found for Favorite Players")
    void testGetUserFavoritePlayersByUserIdNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        List<PlayerWithClubAndTeamResponseDTO> foundPlayers = playerService.getUserFavoritePlayersByUserId(userId);

        assertTrue(foundPlayers.isEmpty());
    }

    @Test
    @DisplayName("Create Player")
    void testCreatePlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player createdPlayer = playerService.createPlayer(playerRequestDTO);

        assertNotNull(createdPlayer);
        assertEquals("Player Name", createdPlayer.getName());
    }

    @Test
    @DisplayName("Update Player")
    void testUpdatePlayer() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player updatedPlayer = playerService.updatePlayer(playerId, playerUpdateRequestDTO);

        assertNotNull(updatedPlayer);
        assertEquals("Updated Player Name", updatedPlayer.getName());
    }

    @Test
    @DisplayName("Player Not Found while updating")
    void testUpdatePlayerNotFound() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        Player updatedPlayer = playerService.updatePlayer(playerId, playerUpdateRequestDTO);

        assertNull(updatedPlayer);
    }

    @Test
    @DisplayName("Delete Player")
    void testDeletePlayer() {
        when(playerRepository.existsById(playerId)).thenReturn(true);
        doNothing().when(playerRepository).deleteById(playerId);

        boolean deleted = playerService.deletePlayer(playerId);

        assertTrue(deleted);
        verify(playerRepository, times(1)).deleteById(playerId);
    }

    @Test
    @DisplayName("Player Not Found while deleting")
    void testDeletePlayerNotFound() {
        when(playerRepository.existsById(playerId)).thenReturn(false);

        boolean deleted = playerService.deletePlayer(playerId);

        assertFalse(deleted);
        verify(playerRepository, never()).deleteById(playerId);
    }
}
