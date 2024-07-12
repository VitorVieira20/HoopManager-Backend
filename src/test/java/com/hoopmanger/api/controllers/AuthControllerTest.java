package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.RegisterRequestDTO;
import com.hoopmanger.api.infra.security.TokenService;
import com.hoopmanger.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName( "Register new user" )
    void testRegisterNewUser( ) throws Exception {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO(
                "newuser@example.com", "password", "New User", "user", "standard"
        );

        doReturn( Optional.empty( ) ).when( userRepository ).findByEmail( "newuser@example.com" );
        doReturn( "encodedPassword" ).when( passwordEncoder ).encode( "password" );

        User savedUser = new User( );
        savedUser.setId( UUID.randomUUID( ) );
        savedUser.setEmail( "newuser@example.com" );
        savedUser.setName( "New User" );
        savedUser.setPassword( "encodedPassword" );
        savedUser.setRole( "user" );
        savedUser.setPlan( "standard" );
        savedUser.setClubs( new ArrayList<>( ) );
        savedUser.setTeams( new ArrayList<>( ) );
        savedUser.setGames( new ArrayList<>( ) );
        savedUser.setAthletes( new ArrayList<>( ) );

        doReturn( savedUser ).when( userRepository ).save( any( User.class ) );
        doReturn( "testToken" ).when( tokenService ).generateToken( any( User.class ) );

        mockMvc.perform( post( "/api/auth/register" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"email\": \"newuser@example.com\", \"password\": \"password\", \"name\": \"New User\", \"role\": \"user\", \"plan\": \"standard\"}" ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.email" ).value( "newuser@example.com" ) )
                .andExpect( jsonPath( "$.token" ).value( "testToken" ) );
    }

    @Test
    @DisplayName( "Register existing user" )
    void testRegisterExistingUser( ) throws Exception {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO(
                "existinguser@example.com", "password", "Existing User", "user", "standard"
        );

        User existingUser = new User( );
        existingUser.setEmail( "existinguser@example.com" );

        doReturn( Optional.of( existingUser ) ).when( userRepository ).findByEmail( "existinguser@example.com" );

        mockMvc.perform( post( "/api/auth/register" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"email\": \"existinguser@example.com\", \"password\": \"password\", \"name\": \"Existing User\", \"role\": \"user\", \"plan\": \"standard\"}" ) )
                .andExpect( status( ).isBadRequest( ) );
    }

    @Test
    @DisplayName( "Login with valid credentials" )
    void testLoginWithValidCredentials( ) throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO( "user@example.com", "correctpassword" );

        User user = new User( );
        user.setEmail( loginRequest.email( ) );
        user.setPassword( "encodedPassword" );
        user.setName( "Test User" );
        user.setId( UUID.randomUUID( ) );
        user.setRole( "user" );
        user.setPlan( "standard" );
        user.setClubs( new ArrayList<>( ) );
        user.setTeams( new ArrayList<>( ) );
        user.setGames( new ArrayList<>( ) );
        user.setAthletes( new ArrayList<>( ) );

        when( userRepository.findByEmail( loginRequest.email( ) ) ).thenReturn( Optional.of( user ) );
        when( passwordEncoder.matches( loginRequest.password( ), user.getPassword( ) ) ).thenReturn( true );
        when( tokenService.generateToken( user ) ).thenReturn( "testToken" );

        mockMvc.perform( post( "/api/auth/login" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"email\": \"user@example.com\", \"password\": \"correctpassword\"}" ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( jsonPath( "$.email" ).value( "user@example.com" ) )
                .andExpect( jsonPath( "$.token" ).value( "testToken" ) );
    }

    @Test
    @DisplayName("Login with invalid credentials")
    void testLoginWithInvalidCredentials() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("invaliduser@example.com", "wrongpassword");

        User user = new User();
        user.setEmail(loginRequest.email());
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invaliduser@example.com\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isBadRequest());
    }
}
