package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.game.GameUpdateRequestDTO;
import com.hoopmanger.api.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private UUID gameId;
    private UUID teamId;
    private UUID ownerId;
    private Game game;
    private List<Game> games;
    private GameRequestDTO gameRequestDTO;
    private GameUpdateRequestDTO gameUpdateRequestDTO;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.openMocks( this );
        gameId = UUID.randomUUID( );
        teamId = UUID.randomUUID( );
        ownerId = UUID.randomUUID( );

        game = new Game( );
        game.setId( gameId );
        game.setTeam_id( teamId );
        game.setHome_team( "Home Team" );
        game.setAway_team( "Away Team" );
        game.setHome_score( 10 );
        game.setAway_score( 5 );
        game.setLocation( "Location" );
        game.setDate( new Date( ) );

        games = Arrays.asList( game );

        gameRequestDTO = new GameRequestDTO( teamId, new Date( ), "New Home Team", "New Away Team", 20, 10, "New Location" );

        gameUpdateRequestDTO = new GameUpdateRequestDTO( new Date( ), "Updated Home Team", "Updated Away Team", 20, 10, "Updated Location" );
    }

    @Test
    @DisplayName( "Get Game by ID" )
    void testGetGameById( ) {
        when( gameRepository.findGameById( gameId ) ).thenReturn( game );

        Game foundGame = gameService.getGameById( gameId );

        assertNotNull( foundGame );
        assertEquals( "Home Team", foundGame.getHome_team( ) );
    }

    @Test
    @DisplayName( "Get Games by Team ID" )
    void testGetGamesByTeamId( ) {
        when( gameRepository.findGamesByTeamId( teamId ) ).thenReturn( games );

        List<Game> foundGames = gameService.getGamesByTeamId( teamId );

        assertFalse( foundGames.isEmpty( ) );
        assertEquals( 1, foundGames.size( ) );
        assertEquals( "Home Team", foundGames.get( 0 ).getHome_team( ) );
    }

    @Test
    @DisplayName( "Get Games by Owner ID" )
    void testGetGamesByOwnerId( ) {
        when( gameRepository.findGamesByOwnerId( ownerId ) ).thenReturn( games );

        List<Game> foundGames = gameService.getGamesByOwnerId( ownerId );

        assertFalse( foundGames.isEmpty( ) );
        assertEquals( 1, foundGames.size( ) );
        assertEquals( "Home Team", foundGames.get( 0 ).getHome_team( ) );
    }

    @Test
    @DisplayName( "Create Game" )
    void testCreateGame( ) {
        Game gameWithNewHomeTeam = new Game( );
        gameWithNewHomeTeam.setHome_team( "New Home Team" );

        when( gameRepository.save( any( Game.class ) ) ).thenReturn( gameWithNewHomeTeam );

        Game createdGame = gameService.createGame( gameRequestDTO );

        assertNotNull( createdGame );
        assertEquals( "New Home Team", createdGame.getHome_team( ) );
    }

    @Test
    @DisplayName( "Update Game" )
    void testUpdateGame( ) {
        when( gameRepository.findById( gameId ) ).thenReturn( Optional.of( game ) );
        when( gameRepository.save( any( Game.class ) ) ).thenReturn( game );

        Game updatedGame = gameService.updateGame( gameId, gameUpdateRequestDTO );

        assertNotNull( updatedGame );
        assertEquals( "Updated Home Team", updatedGame.getHome_team( ) );
    }

    @Test
    @DisplayName( "Game Not Found while updating" )
    void testUpdateGameNotFound( ) {
        when( gameRepository.findById( gameId ) ).thenReturn( Optional.empty( ) );

        Game updatedGame = gameService.updateGame( gameId, gameUpdateRequestDTO );

        assertNull( updatedGame );
    }

    @Test
    @DisplayName( "Delete Game" )
    void testDeleteGame( ) {
        when( gameRepository.existsById( gameId ) ).thenReturn( true );
        doNothing( ).when( gameRepository ).deleteById( gameId );

        boolean deleted = gameService.deleteGame( gameId );

        assertTrue( deleted );
        verify( gameRepository, times( 1 ) ).deleteById( gameId );
    }

    @Test
    @DisplayName( "Game Not Found while deleting" )
    void testDeleteGameNotFound( ) {
        when( gameRepository.existsById( gameId ) ).thenReturn( false );

        boolean deleted = gameService.deleteGame( gameId );

        assertFalse( deleted );
        verify( gameRepository, never( ) ).deleteById( gameId );
    }
}
