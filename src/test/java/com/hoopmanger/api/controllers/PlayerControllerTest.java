package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.player.Player;
import com.hoopmanger.api.domain.player.PlayerRequestDTO;
import com.hoopmanger.api.domain.player.PlayerUpdateRequestDTO;
import com.hoopmanger.api.domain.player.PlayerWithClubAndTeamResponseDTO;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenTestService;
import com.hoopmanger.api.services.PlayerService;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private TokenTestService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID playerId;
    private UUID gameId;
    private UUID teamId;
    private UUID clubId;
    private UUID ownerId;
    private Player player;
    private PlayerWithClubAndTeamResponseDTO playerWithInfo;
    private Game game;
    private Team team;
    private Club club;
    private User owner;
    private List<Player> players;
    private List<PlayerWithClubAndTeamResponseDTO> playersWithInfo;
    private PlayerRequestDTO playerRequestDTO;
    private PlayerUpdateRequestDTO playerUpdateRequestDTO;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        playerId = UUID.randomUUID( );
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
        game.setTeam_id( teamId );

        player = new Player( );
        player.setId( playerId );
        player.setTeam_id( teamId );

        playerWithInfo = new PlayerWithClubAndTeamResponseDTO( );
        playerWithInfo.setId( playerId );
        playerWithInfo.setName( "Test Player" );
        playerWithInfo.setTeam_id( teamId );

        players = Arrays.asList( player );
        playersWithInfo = Arrays.asList( playerWithInfo );

        playerRequestDTO = new PlayerRequestDTO( teamId, "New Test Player", "New Test Position" );

        playerUpdateRequestDTO = new PlayerUpdateRequestDTO( "Updated Test Player", "Updated Test Position" );

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
    @DisplayName( "Get Player by ID" )
    void testGetPlayerById( ) throws Exception {
        when( playerService.getPlayerById( playerId ) ).thenReturn( playerWithInfo );

        mockMvc.perform( get( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Player by ID - No Content" )
    void testGetPlayerByIdNoContent( ) throws Exception {
        when( playerService.getPlayerById( playerId ) ).thenReturn( null );

        mockMvc.perform( get( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Players by Team ID" )
    void testGetPlayersByTeamId( ) throws Exception {
        when( playerService.getPlayersByTeamId( teamId ) ).thenReturn( players );

        mockMvc.perform( get( "/api/player/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Players by Team ID - No Content" )
    void testGetPlayersByTeamIdNoContent( ) throws Exception {
        when( playerService.getPlayersByTeamId( teamId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Players by Owner ID" )
    void testGetPlayersByOwnerId( ) throws Exception {
        when( playerService.getPlayersByOwnerId( ownerId ) ).thenReturn( players );

        mockMvc.perform( get( "/api/player/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Players by Owner ID - No Content" )
    void testGetPlayersByOwnerIdNoContent( ) throws Exception {
        when( playerService.getPlayersByOwnerId( ownerId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Players by Game ID" )
    void testGetPlayersByGameId( ) throws Exception {
        when( playerService.getPlayersByGameId( gameId ) ).thenReturn( players );

        mockMvc.perform( get( "/api/player/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Players by Game ID - No Content" )
    void testGetPlayersByGameIdNoContent( ) throws Exception {
        when( playerService.getPlayersByGameId( gameId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/game/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Remaining Players from Game Info by Game ID" )
    void testGetRemainingPlayersFromGameInfoByGameId( ) throws Exception {
        when( playerService.getRemainingPlayersFromGameInfoByGameId( gameId ) ).thenReturn( players );

        mockMvc.perform( get( "/api/player/gameInfo/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Remaining Players from Game Info by Game ID - No Content" )
    void testGetRemainingPlayersFromGameInfoByGameIdNoContent( ) throws Exception {
        when( playerService.getRemainingPlayersFromGameInfoByGameId( gameId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/gameInfo/{gameId}", gameId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Players by Name" )
    void testGetPlayersByName( ) throws Exception {
        when( playerService.getPlayersByName( anyString( ) ) ).thenReturn( playersWithInfo );

        mockMvc.perform( get( "/api/player/name/{playerName}", "Test Player" )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get Players by Name - No Content" )
    void testGetPlayersByNameNoContent( ) throws Exception {
        when( playerService.getPlayersByName( anyString( ) ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/name/{playerName}", "Test Player" )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get User Favorite Players by User ID" )
    void testGetUserFavoritePlayersByUserId( ) throws Exception {
        when( playerService.getUserFavoritePlayersByUserId( any( UUID.class ) ) ).thenReturn( playersWithInfo );

        mockMvc.perform( get( "/api/player/favs/{userId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Get User Favorite Players by User ID - No Content" )
    void testGetUserFavoritePlayersByUserIdNoContent( ) throws Exception {
        when( playerService.getUserFavoritePlayersByUserId( any( UUID.class ) ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/player/favs/{userId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Create Player" )
    void testCreatePlayer( ) throws Exception {
        when( playerService.createPlayer( any( PlayerRequestDTO.class ) ) ).thenReturn( player );

        String playerRequestJson = objectMapper.writeValueAsString( playerRequestDTO );

        mockMvc.perform( post( "/api/player/" )
                        .header( HttpHeaders.AUTHORIZATION, token )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( playerRequestJson ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Update Player" )
    void testUpdatePlayer( ) throws Exception {
        when( playerService.updatePlayer( any( UUID.class ), any( PlayerUpdateRequestDTO.class ) ) ).thenReturn( player );

        String playerUpdateJson = objectMapper.writeValueAsString( playerUpdateRequestDTO );

        mockMvc.perform( put( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( playerUpdateJson ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.id" ).value( playerId.toString( ) ) );
    }

    @Test
    @DisplayName( "Player Not Found while Updating " )
    void testUpdatePlayerNotFound( ) throws Exception {
        when( playerService.updatePlayer( any( UUID.class ), any( PlayerUpdateRequestDTO.class ) ) ).thenReturn( null );

        String playerUpdateJson = objectMapper.writeValueAsString( playerUpdateRequestDTO );

        mockMvc.perform( put( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( playerUpdateJson ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Delete Player" )
    void testDeletePlayer( ) throws Exception {
        when( playerService.deletePlayer( any( UUID.class ) ) ).thenReturn( true );

        mockMvc.perform( delete( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token )
                        .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Player Not Found while deleting" )
    void testDeletePlayerNotFound( ) throws Exception {
        when( playerService.deletePlayer( any( UUID.class ) ) ).thenReturn( false );

        mockMvc.perform( delete( "/api/player/{playerId}", playerId )
                        .header( HttpHeaders.AUTHORIZATION, token )
                        .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status( ).isNotFound( ) );
    }
}
