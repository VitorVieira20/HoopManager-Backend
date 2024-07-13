package com.hoopmanger.api.infra.security;

import com.hoopmanger.api.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTestServiceTest {

    @InjectMocks
    private TokenTestService tokenTestService;

    @BeforeEach
    public void setUp( ) {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    @DisplayName( "Test Generate Token" )
    public void testGenerateToken( ) {
        User user = new User( );
        user.setEmail( "user@example.com" );

        String token = tokenTestService.generateToken( user );

        assertNotNull( token );
    }
}
