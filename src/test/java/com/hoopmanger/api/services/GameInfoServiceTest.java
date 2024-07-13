package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.gameInfo.*;
import com.hoopmanger.api.repositories.GameInfoRepository;
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
public class GameInfoServiceTest {

    @Mock
    private GameInfoRepository gameInfoRepository;

    @InjectMocks
    private GameInfoService gameInfoService;

    private Integer gameInfoId;
    private UUID playerId;
    private UUID gameId;
    private GameInfo gameInfo;
    private GameInfoWithPlayerNameResponseDTO gameInfoWithPlayerName;
    private List<GameInfoWithPlayerNameResponseDTO> gameInfosWithPlayerName;
    private GameInfoRequestDTO gameInfoRequestDTO;
    private GameInfoUpdateRequestDTO gameInfoUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameInfoId = 1;
        playerId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        gameInfo = new GameInfo();
        gameInfo.setId(gameInfoId);
        gameInfo.setPlayer_id(playerId);
        gameInfo.setPoints(10);
        gameInfo.setAssists(5);
        gameInfo.setRebounds(7);
        gameInfo.setGame_id(gameId);

        gameInfoWithPlayerName = new GameInfoWithPlayerNameResponseDTO(gameInfoId, playerId, 10, 5, 7, gameId, "Player Name");

        gameInfosWithPlayerName = Collections.singletonList(gameInfoWithPlayerName);

        gameInfoRequestDTO = new GameInfoRequestDTO(playerId, 10, 5, 7, gameId);

        gameInfoUpdateRequestDTO = new GameInfoUpdateRequestDTO(15, 7, 10);
    }

    @Test
    @DisplayName("Get GameInfo with Player Name by Game ID")
    void testGetGameInfoWithPlayerName() {
        when(gameInfoRepository.findGameInfoWithPlayerNameByGameId(gameId)).thenReturn(gameInfosWithPlayerName);

        List<GameInfoWithPlayerNameResponseDTO> foundGameInfos = gameInfoService.getGameInfoWithPlayerName(gameId);

        assertFalse(foundGameInfos.isEmpty());
        assertEquals(1, foundGameInfos.size());
        assertEquals("Player Name", foundGameInfos.get(0).getPlayer_name());
    }

    @Test
    @DisplayName("Get GameInfo by ID")
    void testGetGameInfoById() {
        when(gameInfoRepository.findGameInfoById(gameInfoId)).thenReturn(gameInfoWithPlayerName);

        GameInfoWithPlayerNameResponseDTO foundGameInfo = gameInfoService.getGameInfoById(gameInfoId);

        assertNotNull(foundGameInfo);
        assertEquals("Player Name", foundGameInfo.getPlayer_name());
    }

    @Test
    @DisplayName("Create GameInfo")
    void testCreateGameInfo() {
        when(gameInfoRepository.save(any(GameInfo.class))).thenReturn(gameInfo);

        GameInfo createdGameInfo = gameInfoService.createGameInfo(gameInfoRequestDTO);

        assertNotNull(createdGameInfo);
        assertEquals(10, createdGameInfo.getPoints());
    }

    @Test
    @DisplayName("Update GameInfo")
    void testUpdateGameInfo() {
        when(gameInfoRepository.findById(gameInfoId)).thenReturn(Optional.of(gameInfo));
        when(gameInfoRepository.save(any(GameInfo.class))).thenReturn(gameInfo);

        GameInfo updatedGameInfo = gameInfoService.updateGameInfo(gameInfoId, gameInfoUpdateRequestDTO);

        assertNotNull(updatedGameInfo);
        assertEquals(15, updatedGameInfo.getPoints());
    }

    @Test
    @DisplayName("GameInfo Not Found while updating")
    void testUpdateGameInfoNotFound() {
        when(gameInfoRepository.findById(gameInfoId)).thenReturn(Optional.empty());

        GameInfo updatedGameInfo = gameInfoService.updateGameInfo(gameInfoId, gameInfoUpdateRequestDTO);

        assertNull(updatedGameInfo);
    }

    @Test
    @DisplayName("Delete GameInfo")
    void testDeleteGameInfo() {
        when(gameInfoRepository.existsById(gameInfoId)).thenReturn(true);
        doNothing().when(gameInfoRepository).deleteById(gameInfoId);

        boolean deleted = gameInfoService.deleteGameInfo(gameInfoId);

        assertTrue(deleted);
        verify(gameInfoRepository, times(1)).deleteById(gameInfoId);
    }

    @Test
    @DisplayName("GameInfo Not Found while deleting")
    void testDeleteGameInfoNotFound() {
        when(gameInfoRepository.existsById(gameInfoId)).thenReturn(false);

        boolean deleted = gameInfoService.deleteGameInfo(gameInfoId);

        assertFalse(deleted);
        verify(gameInfoRepository, never()).deleteById(gameInfoId);
    }
}
