package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.services.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClubController.class)
public class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID clubId;
    private UUID ownerId;
    private Club club;
    private List<Club> clubs;
    private ClubRequestDTO clubRequestDTO;
    private ClubUpdateRequestDTO clubUpdateRequestDTO;

    @BeforeEach
    void setUp( ) {
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

        clubs = Arrays.asList( club );

        clubRequestDTO = new ClubRequestDTO( ownerId, "New Club", "newclub@example.com", 987654321, "new_instagram", "new_twitter", "new_facebook" );

        clubUpdateRequestDTO = new ClubUpdateRequestDTO( "Updated Club", "updatedclub@example.com", 1122334455, "updated_instagram", "updated_twitter", "updated_facebook" );
    }

    @Test
    @DisplayName( "Get Club by ID" )
    void testGetClubById( ) throws Exception {
        when( clubService.getClubById( clubId ) ).thenReturn( club );

        mockMvc.perform( get("/api/club/{clubId}", clubId ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test Club" ) );
    }

    @Test
    @DisplayName( "Get Club Not Found by ID" )
    void testGetClubByIdNotFound( ) throws Exception {
        when( clubService.getClubById( clubId ) ).thenReturn( null );

        mockMvc.perform( get( "/api/club/{clubId}", clubId ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Get Clubs by Owner ID" )
    void testGetClubsByOwnerId( ) throws Exception {
        when( clubService.getClubsByOwnerId( ownerId ) ).thenReturn( clubs );

        mockMvc.perform( get( "/api/club/owner/{ownerId}", ownerId ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Test Club" ) );
    }

    @Test
    @DisplayName( "Get Clubs Not Found by Owner ID" )
    void testGetClubsByOwnerIdNotFound( ) throws Exception {
        when( clubService.getClubsByOwnerId( ownerId ) ).thenReturn( Arrays.asList( ) );

        mockMvc.perform( get( "/api/club/owner/{ownerId}", ownerId ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Create Club" )
    void testCreateClub( ) throws Exception {
        when( clubService.createClub( any( ClubRequestDTO.class ) ) ).thenReturn( club );

        mockMvc.perform( post( "/api/club/" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( clubRequestDTO ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test Club" ) );
    }

    @Test
    @DisplayName( "Update Club" )
    void testUpdateClub( ) throws Exception {
        when( clubService.updateClub( any( UUID.class ), any( ClubUpdateRequestDTO.class ) ) ).thenReturn( club );

        mockMvc.perform( put( "/api/club/{clubId}", clubId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( clubUpdateRequestDTO ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test Club" ) );
    }

    @Test
    @DisplayName( "Club Not Found wile updating" )
    void testUpdateClubNotFound( ) throws Exception {
        when( clubService.updateClub( any( UUID.class ), any( ClubUpdateRequestDTO.class ) ) ).thenReturn( null );

        mockMvc.perform( put("/api/club/{clubId}", clubId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( clubUpdateRequestDTO ) ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Delete Club" )
    void testDeleteClub( ) throws Exception {
        when( clubService.deleteClub( clubId ) ).thenReturn( true );

        mockMvc.perform( delete( "/api/club/{clubId}", clubId ) )
                .andExpect( status( ).isNoContent( ) );
    }

    @Test
    @DisplayName( "Club Not Found while deleting" )
    void testDeleteClubNotFound( ) throws Exception {
        when( clubService.deleteClub( clubId ) ).thenReturn( false );

        mockMvc.perform( delete( "/api/club/{clubId}", clubId ) )
                .andExpect( status( ).isNotFound( ) );
    }
}
