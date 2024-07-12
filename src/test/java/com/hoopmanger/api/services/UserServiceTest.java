package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.user.*;
import com.hoopmanger.api.repositories.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private User user;
    private UserResponseDTO userResponseDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.openMocks( this );
        userId = UUID.randomUUID( );

        user = new User( );
        user.setId( userId );
        user.setName( "User Name" );
        user.setEmail( "user@example.com" );
        user.setRole( "User" );
        user.setPlan( "Plan" );
        user.setClubs( Collections.singletonList( UUID.randomUUID( ) ) );
        user.setTeams( Collections.singletonList( UUID.randomUUID( ) ) );
        user.setGames( Collections.singletonList( UUID.randomUUID( ) ) );
        user.setAthletes( Collections.singletonList( UUID.randomUUID( ) ) );

        userResponseDTO = new UserResponseDTO(
                userId, "User Name", "user@example.com", "User", "Plan",
                Collections.singletonList( UUID.randomUUID( ) ), Collections.singletonList( UUID.randomUUID( ) ),
                Collections.singletonList( UUID.randomUUID( ) ), Collections.singletonList( UUID.randomUUID( ) )
        );

        userUpdateRequestDTO = new UserUpdateRequestDTO(
                Collections.singletonList( UUID.randomUUID( ) ), Collections.singletonList( UUID.randomUUID( ) ),
                Collections.singletonList( UUID.randomUUID( ) ), Collections.singletonList( UUID.randomUUID( ) )
        );
    }

    @Test
    @DisplayName( "Get User by ID" )
    void testGetUserById( ) {
        when( userRepository.findById( userId ) ).thenReturn( Optional.of( user ) );

        Optional<UserResponseDTO> foundUser = userService.getUserById( userId );

        assertTrue( foundUser.isPresent( ) );
        assertEquals( "User Name", foundUser.get( ).name( ) );
    }

    @Test
    @DisplayName( "User Not Found" )
    void testGetUserByIdNotFound( ) {
        when( userRepository.findById( userId ) ).thenReturn( Optional.empty( ) );

        Optional<UserResponseDTO> foundUser = userService.getUserById( userId );

        assertFalse( foundUser.isPresent( ) );
    }

    @Test
    @DisplayName( "Update User" )
    void testUpdateUser( ) {
        when( userRepository.findById( userId ) ).thenReturn( Optional.of( user ) );
        when( userRepository.save( any( User.class ) ) ).thenReturn( user );

        Optional<UserResponseDTO> updatedUser = userService.updateUser( userId, userUpdateRequestDTO );

        assertTrue( updatedUser.isPresent( ) );
        assertEquals( "User Name", updatedUser.get( ).name( ) );
    }

    @Test
    @DisplayName( "Update User Not Found" )
    void testUpdateUserNotFound( ) {
        when( userRepository.findById( userId ) ).thenReturn( Optional.empty( ) );

        Optional<UserResponseDTO> updatedUser = userService.updateUser( userId, userUpdateRequestDTO );

        assertFalse( updatedUser.isPresent( ) );
    }
}
