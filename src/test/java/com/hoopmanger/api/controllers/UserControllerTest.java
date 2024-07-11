package com.hoopmanger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.domain.user.UserUpdateRequestDTO;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenTestService;
import com.hoopmanger.api.services.UserService;
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

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private TokenTestService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private User user;
    private UserResponseDTO userResponseDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;
    private String token;


    @BeforeEach
    void setUp() throws Exception {
        userId = UUID.randomUUID();
        /*user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("123");
        user.setRole("user");
        user.setPlan("standard");
        user.setClubs(new ArrayList<UUID>());
        user.setTeams(new ArrayList<UUID>());
        user.setGames(new ArrayList<UUID>());
        user.setAthletes(new ArrayList<UUID>());*/

        userResponseDTO = new UserResponseDTO( userId, "Test User", "test@test.com", "admin", "standard", new ArrayList<UUID>( ), new ArrayList<UUID>( ), new ArrayList<UUID>( ), new ArrayList<UUID>( ) );

        userUpdateRequestDTO = new UserUpdateRequestDTO( new ArrayList<UUID>( ), new ArrayList<UUID>( ), new ArrayList<UUID>( ), new ArrayList<UUID>( ) );

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO( "user1@gmail.com", "123" );
        String loginRequestBody = objectMapper.writeValueAsString( loginRequestDTO );

        mockMvc.perform(post( "/api/auth/login" )
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
    @DisplayName( "Get User by ID" )
    void testGetUserById( ) throws Exception {
        when( userService.getUserById( userId ) ).thenReturn( Optional.of( userResponseDTO ) );

        mockMvc.perform( get( "/api/user/{id}", userId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath("$.name" ).value( "Test User" ) );
    }

    @Test
    @DisplayName( "Get User Not Found by ID" )
    void testGetUserByIdNotFound( ) throws Exception {
        when( userService.getUserById( userId ) ).thenReturn( Optional.empty( ) );

        mockMvc.perform( get( "/api/user/{id}", userId )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }

    @Test
    @DisplayName( "Update User" )
    void testUpdateUser( ) throws Exception {
        when( userService.updateUser( userId, userUpdateRequestDTO ) ).thenReturn( Optional.of( userResponseDTO ) );

        mockMvc.perform( put( "/api/user/{userId}", userId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( userUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.name" ).value( "Test User" ) );
    }

    @Test
    @DisplayName( "User Not Found while updating" )
    void testUpdateUserNotFound( ) throws Exception {
        when( userService.updateUser( userId, userUpdateRequestDTO ) ).thenReturn( Optional.empty( ) );

        mockMvc.perform( put( "/api/user/{userId}", userId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( userUpdateRequestDTO ) )
                        .header( HttpHeaders.AUTHORIZATION, token ) )
                .andExpect( status( ).isNotFound( ) );
    }

}
