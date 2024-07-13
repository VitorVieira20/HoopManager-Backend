package com.hoopmanger.api.infra.security;

import com.hoopmanger.api.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setUp( ) {
        MockitoAnnotations.openMocks( this );
        ReflectionTestUtils.setField( tokenService, "secret", "my-mock-secret" );
    }

    @Test
    @DisplayName( "Test Generate Token" )
    public void testGenerateToken( ) {
        User user = new User( );
        user.setEmail( "user@example.com" );

        String token = tokenService.generateToken( user );

        assertNotNull( token );
    }

    @Test
    @DisplayName( "Test Validate Token" )
    public void testValidateToken_ValidToken( ) {
        User user = new User( );
        user.setEmail( "user@example.com" );

        String token = tokenService.generateToken( user );

        String validatedEmail = tokenService.validateToken( token );

        assertEquals( user.getEmail( ), validatedEmail );
    }

    @Test
    @DisplayName( "Test Validate Invalid Token" )
    public void testValidateToken_InvalidToken( ) {
        String token = "invalid-token";

        String validatedEmail = tokenService.validateToken( token );

        assertNull( validatedEmail );
    }
}
