package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.team.Team;
import com.hoopmanger.api.domain.team.TeamRequestDTO;
import com.hoopmanger.api.domain.team.TeamUpdateRequestDTO;
import com.hoopmanger.api.domain.team.TeamWithClubDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenTestService;
import com.hoopmanger.api.services.TeamService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeamService teamService;
    @MockBean
    private TokenTestService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID teamId;
    private UUID teamWithClubId;
    private UUID clubId;
    private UUID ownerId;
    private Team team;
    private Club club;
    private User owner;
    private TeamWithClubDTO teamWithClub;
    private List<Team> teams;
    private List<TeamWithClubDTO> teamsWithClub;
    private List<UUID> ownerFavTeams;
    private TeamRequestDTO teamRequestDTO;
    private TeamUpdateRequestDTO teamUpdateRequestDTO;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        teamId = UUID.randomUUID( );
        teamWithClubId = UUID.randomUUID( );
        clubId = UUID.randomUUID( );
        ownerId = UUID.randomUUID( );

        club = new Club( );
        club.setId( clubId );
        club.setName( "Test Club" );
        club.setEmail( "test@example.com" );
        club.setPhone( 123456789 );
        club.setInstagram( "test_instagram" );
        club.setTwitter( "test_twitter" );
        club.setFacebook( "test_facebook" );
        club.setOwner_id( ownerId );

        team = new Team( );
        team.setId( teamId );
        team.setName( "Test Team" );
        team.setClub_id( clubId );

        teamWithClub = new TeamWithClubDTO( );
        teamWithClub.setId( teamWithClubId );
        teamWithClub.setName( "Test TeamWithClub" );
        teamWithClub.setClubId( clubId );
        teamWithClub.setClubName( "Test ClubForTeam" );

        teams = Arrays.asList( team );
        teamsWithClub = Arrays.asList( teamWithClub );
        ownerFavTeams = Arrays.asList( teamWithClub.getId( ) );

        owner = new User( );
        owner.setId( ownerId );
        owner.setTeams( ownerFavTeams );

        teamRequestDTO = new TeamRequestDTO( clubId, "New Team" );

        teamUpdateRequestDTO = new TeamUpdateRequestDTO( "Updated Team" );

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
    @DisplayName( "Get Team by ID" )
    void testGetTeamById( ) throws Exception {
        when( teamService.getTeamById( teamWithClubId ) ).thenReturn( teamWithClub );

        mockMvc.perform( get( "/api/team/{teamId}", teamWithClubId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test TeamWithClub" ) );
    }

    @Test
    @DisplayName( "Get Club Not Found by ID" )
    void testGetTeamsByIdNotFound( ) throws Exception {
        when(teamService.getTeamById( teamWithClubId ) ).thenReturn( null );

        mockMvc.perform( get ( "/api/team/{teamId}", teamWithClubId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Teams by Club ID" )
    void testGetTeamsByClubId( ) throws Exception {
        when( teamService.getTeamsByClubId( clubId ) ).thenReturn( teams );

        mockMvc.perform( get( "/api/team/club/{clubId}", clubId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Test Team" ) );
    }

    @Test
    @DisplayName( "Get Teams Not Found by Club ID" )
    void testGetTeamsByClubIdNotFound( ) throws Exception {
        when( teamService.getTeamsByClubId( clubId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/team/club/{clubId}", clubId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Teams by Owner ID" )
    void testGetTeamsByOwnerId( ) throws Exception {
        when( teamService.getTeamsByOwnerId( ownerId ) ).thenReturn( teams );

        mockMvc.perform( get( "/api/team/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Test Team" ) );
    }

    @Test
    @DisplayName( "Get Teams Not Found by Owner ID" )
    void testGetTeamsByOwnerIdNotFound( ) throws Exception {
        when( teamService.getTeamsByOwnerId( ownerId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/team/owner/{ownerId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Teams by Team Name" )
    void testGetTeamsByTeamName( ) throws Exception {
        when( teamService.getTeamsByName( "Test TeamWithClub" ) ).thenReturn( teamsWithClub );

        String teamName = "Test TeamWithClub";

        mockMvc.perform( get( "/api/team/name/{teamName}", teamName )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Test TeamWithClub" ) );
    }

    @Test
    @DisplayName( "Get Teams Not Found by Team Name" )
    void testGetTeamsByTeamNameNotFound( ) throws Exception {
        when( teamService.getTeamsByName( "Test TeamWithClub" ) ).thenReturn( Arrays.asList( ) );

        String teamName = "Test TeamWithClub";

        mockMvc.perform( get( "/api/team/name/{teamName}", teamName )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get User Favorite Teams By User ID" )
    void testGetUserFavoriteTeamsByUserId( ) throws Exception {
        when( teamService.getUserFavoriteTeamsByUserId( ownerId ) ).thenReturn( teamsWithClub );

        mockMvc.perform( get( "/api/team/favs/{userId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Test TeamWithClub" ) );
    }

    @Test
    @DisplayName( "Get User Favorite Teams Not Found By User ID" )
    void testGetUserFavoriteTeamsByUserIdNotFound( ) throws Exception {
        when( teamService.getUserFavoriteTeamsByUserId( ownerId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/team/favs/{userId}", ownerId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Create Team" )
    void testCreateTeam( ) throws Exception {
        when( teamService.createTeam( any( TeamRequestDTO.class ) ) ).thenReturn( team );

        mockMvc.perform( post( "/api/team/" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( teamRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test Team" ) );
    }

    @Test
    @DisplayName( "Update Team" )
    void testUpdateTeam( ) throws Exception {
        when( teamService.updateTeam( any( UUID.class ), any( TeamUpdateRequestDTO.class ) ) ).thenReturn( team );

        mockMvc.perform( put( "/api/team/{teamId}", teamId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( teamUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test Team" ) );
    }

    @Test
    @DisplayName( "Team Not Found while updating" )
    void testUpdateTeamNotFound( ) throws Exception {
        when( teamService.updateTeam( any( UUID.class ), any( TeamUpdateRequestDTO.class ) ) ).thenReturn( null );

        mockMvc.perform( put( "/api/team/{teamId}", teamId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( teamUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Delete Team" )
    void testDeleteTeam( ) throws Exception {
        when( teamService.deleteTeam( teamId ) ).thenReturn( true );

        mockMvc.perform( delete( "/api/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Team Not Found while deleting" )
    void testDeleteTeamNotFound( ) throws Exception {
        when( teamService.deleteTeam( teamId ) ).thenReturn( false );

        mockMvc.perform( delete( "/api/team/{teamId}", teamId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }
}
