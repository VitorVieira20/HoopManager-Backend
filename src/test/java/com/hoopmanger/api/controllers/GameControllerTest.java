package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.game.GameRequestDTO;
import com.hoopmanger.api.domain.game.GameUpdateRequestDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenTestService;
import com.hoopmanger.api.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService gameService;
    @MockBean
    private TokenTestService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID gameId;
    private UUID teamId;
    private UUID clubId;
    private UUID ownerId;
    private Game game;
    private Team team;
    private Club club;
    private User owner;
    private List<Game> games;
    private GameRequestDTO gameRequestDTO;
    private GameUpdateRequestDTO gameUpdateRequestDTO;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        gameId = UUID.randomUUID( );
        teamId = UUID.randomUUID( );
        clubId = UUID.randomUUID( );
        ownerId = UUID.randomUUID( );

        owner = new User( );
        owner.setId( ownerId );

        club = new Club( );
        club.setId( clubId );
        club.setOwner_id( ownerId );

        team = new Team( );
        team.setId( teamId );
        team.setClub_id( clubId );

        game = new Game( );
        game.setId( gameId );
        game.setHome_team( "Home Team" );
        game.setTeam_id( teamId );

        games = Arrays.asList( game );

        gameRequestDTO = new GameRequestDTO( teamId, new Date( ), "New Home Team", "New Away Team", 20, 10, "New Location" );

        gameUpdateRequestDTO = new GameUpdateRequestDTO( new Date( ), "Updated Home Team", "Updated Away Team", 20, 10, "Updated Location" );

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO( "user1@gmail.com", "123" );
        String loginRequestBody = objectMapper.writeValueAsString( loginRequestDTO );

        mockMvc.perform( post( "/api/auth/login" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( loginRequestBody ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.token" ).exists( ) )
                .andDo( result -> {
                    String responseBody = result.getResponse( ).getContentAsString( );
                    ResponseDTO responseDTO = objectMapper.readValue( responseBody, ResponseDTO.class );
                    token = responseDTO.token( );
                } );
    }


    @Test
    @DisplayName( "Get Game by ID" )
    void testGetGameById( ) throws Exception {
        when( gameService.getGameById( gameId ) ).thenReturn( game );

        mockMvc.perform( get( "/api/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.home_team" ).value( "Home Team" ) );
    }

    @Test
    @DisplayName( "Get Game Not Found by ID" )
    void testGetGameByIdNotFound( ) throws Exception {
        when(gameService.getGameById( gameId ) ).thenReturn( null );

        mockMvc.perform( get ( "/api/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Games by Team ID" )
    void testGetGamesByTeamId( ) throws Exception {
        when( gameService.getGamesByTeamId( teamId ) ).thenReturn( games );

        mockMvc.perform( get( "/api/game/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].home_team" ).value( "Home Team" ) );
    }

    @Test
    @DisplayName( "Get Games Not Found by Team ID" )
    void testGetGamesByTeamIdNotFound( ) throws Exception {
        when( gameService.getGamesByTeamId( teamId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/game/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Games by Owner ID" )
    void testGetGamesByOwnerId( ) throws Exception {
        when( gameService.getGamesByOwnerId( ownerId ) ).thenReturn( games );

        mockMvc.perform( get( "/api/game/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].home_team" ).value( "Home Team" ) );
    }

    @Test
    @DisplayName( "Get Games Not Found by Owner ID" )
    void testGetGamesByOwnerIdNotFound( ) throws Exception {
        when( gameService.getGamesByOwnerId( ownerId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/game/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Create Game" )
    void testCreateGame( ) throws Exception {
        when( gameService.createGame( any( GameRequestDTO.class ) ) ).thenReturn( game );

        mockMvc.perform( post( "/api/game/" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.home_team" ).value( "Home Team" ) );
    }

    @Test
    @DisplayName( "Update Game" )
    void testUpdateGame( ) throws Exception {
        when( gameService.updateGame( any( UUID.class ), any( GameUpdateRequestDTO.class ) ) ).thenReturn( game );

        mockMvc.perform( put( "/api/game/{gameId}", gameId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.home_team" ).value( "Home Team" ) );
    }

    @Test
    @DisplayName( "Game Not Found while updating" )
    void testUpdateGameNotFound( ) throws Exception {
        when( gameService.updateGame( any( UUID.class ), any( GameUpdateRequestDTO.class ) ) ).thenReturn( null );

        mockMvc.perform( put( "/api/game/{gameId}", gameId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Delete Game" )
    void testDeleteGame( ) throws Exception {
        when( gameService.deleteGame( gameId ) ).thenReturn( true );

        mockMvc.perform( delete( "/api/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Game Not Found while deleting" )
    void testDeleteGameNotFound( ) throws Exception {
        when( gameService.deleteGame( gameId ) ).thenReturn( false );

        mockMvc.perform( delete( "/api/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }
}
