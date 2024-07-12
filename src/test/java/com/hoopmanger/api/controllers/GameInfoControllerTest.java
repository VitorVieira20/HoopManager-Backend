package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfoUpdateRequestDTO;
import com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO;
import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenTestService;
import com.hoopmanger.api.services.GameInfoService;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameInfoService gameInfoService;
    @MockBean
    private TokenTestService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private Integer gameInfoId;
    private UUID gameId;
    private UUID playerId;
    private GameInfo gameInfo;
    private GameInfoWithPlayerNameResponseDTO gameInfoWithPlayerNameResponseDTO;
    private Game game;
    private Player player;
    private List<GameInfoWithPlayerNameResponseDTO> gamesInfo;
    private GameInfoRequestDTO gameInfoRequestDTO;
    private GameInfoUpdateRequestDTO gameInfoUpdateRequestDTO;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        gameInfoId = 1;
        gameId = UUID.randomUUID( );
        playerId = UUID.randomUUID( );

        game = new Game( );
        game.setId( gameId );

        gameInfoWithPlayerNameResponseDTO = new GameInfoWithPlayerNameResponseDTO( );
        gameInfoWithPlayerNameResponseDTO.setId( gameInfoId );
        gameInfoWithPlayerNameResponseDTO.setGame_id( gameId );

        gameInfo = new GameInfo( );
        gameInfo.setId( gameInfoId );
        gameInfo.setGame_id( gameId );

        gamesInfo = Arrays.asList( gameInfoWithPlayerNameResponseDTO );

        gameInfoRequestDTO = new GameInfoRequestDTO( playerId, 10, 10, 10, gameId );

        gameInfoUpdateRequestDTO = new GameInfoUpdateRequestDTO( 12, 12, 12 );

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
    @DisplayName( "Get Game Info by ID" )
    void testGetGameInfoById( ) throws Exception {
        when( gameInfoService.getGameInfoById( gameInfoId ) ).thenReturn( gameInfoWithPlayerNameResponseDTO );

        mockMvc.perform( get( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( gameInfoWithPlayerNameResponseDTO.getId( ).toString( ) ) );
    }

    @Test
    @DisplayName( "Get Game Info by ID Not Found" )
    void testGetGameInfoByIdNoContent( ) throws Exception {
        when( gameInfoService.getGameInfoById( gameInfoId ) ).thenReturn( null );

        mockMvc.perform( get( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Game Info by Game ID" )
    void testGetGameInfoByGameId( ) throws Exception {
        when( gameInfoService.getGameInfoWithPlayerName( gameId ) ).thenReturn( gamesInfo );

        mockMvc.perform( get( "/api/gameInfo/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( gameInfoWithPlayerNameResponseDTO.getId( ).toString( ) ) );
    }

    @Test
    @DisplayName( "Get Game Info No Content by Game ID" )
    void testGetGameInfoNoContentByGameId( ) throws Exception {
        when( gameInfoService.getGameInfoWithPlayerName( gameId ) ).thenReturn( Collections.emptyList( ) );

        mockMvc.perform( get( "/api/gameInfo/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Create Game Info" )
    void testCreateGameInfo( ) throws Exception {
        when( gameInfoService.createGameInfo( any( GameInfoRequestDTO.class ) ) ).thenReturn( gameInfo );

        mockMvc.perform( post( "/api/gameInfo/" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameInfoRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( gameInfo.getId( ).toString( ) ) );
    }

    @Test
    @DisplayName( "Update Game Info" )
    void testUpdateGameInfo( ) throws Exception {
        when( gameInfoService.updateGameInfo( any( Integer.class ), any( GameInfoUpdateRequestDTO.class ) ) ).thenReturn( gameInfo );

        mockMvc.perform( put( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameInfoUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( gameInfo.getId( ).toString( ) ) );
    }

    @Test
    @DisplayName( "Game Info Not Found while updating" )
    void testUpdateGameInfoNotFound( ) throws Exception {
        when( gameInfoService.updateGameInfo( any( Integer.class ), any( GameInfoUpdateRequestDTO.class ) ) ).thenReturn( null );

        mockMvc.perform( put( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( gameInfoUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Delete Game Info " )
    void testDeleteGameInfo( ) throws Exception {
        when( gameInfoService.deleteGameInfo( gameInfoId ) ).thenReturn( true );

        mockMvc.perform( delete( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Game Info Not Found while deleting" )
    void testDeleteGameInfoNotFound( ) throws Exception {
        when( gameInfoService.deleteGameInfo( gameInfoId ) ).thenReturn( false );

        mockMvc.perform( delete( "/api/gameInfo/{gameInfoId}", gameInfoId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }
}
