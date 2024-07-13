package com.hoopmanger.api.infra.security;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp( ) {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    @DisplayName( "Test Load USer By Username" )
    public void testLoadUserByUsername_UserExists( ) {
        String email = "user@example.com";
        User user = new User( );
        user.setEmail( email );
        user.setPassword( "password" );

        when( userRepository.findByEmail( email ) ).thenReturn( Optional.of( user ) );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername( email );

        assertNotNull( userDetails );
        assertEquals( email, userDetails.getUsername( ) );
        assertEquals( "password", userDetails.getPassword( ) );
    }

    @Test
    @DisplayName( "Test Load User By Username Not Found" )
    public void testLoadUserByUsername_UserNotFound( ) {
        String email = "nonexistent@example.com";

        when( userRepository.findByEmail( email ) ).thenReturn( Optional.empty( ) );

        assertThrows(UsernameNotFoundException.class, ( ) -> {
            customUserDetailsService.loadUserByUsername( email );
        } );
    }
}
