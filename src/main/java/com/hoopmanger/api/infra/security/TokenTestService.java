package com.hoopmanger.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.hoopmanger.api.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenTestService {
    public String generateToken( User user ){
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-mock-secret");

            String token = JWT.create( )
                    .withIssuer( "hoopmanager-api" )
                    .withSubject( user.getEmail( ) )
                    .withExpiresAt( this.generateExpirationDate( ) )
                    .sign( algorithm );

            return token;
        } catch ( JWTCreationException exception ){
            throw new RuntimeException("Error while authenticating: " + exception.getMessage(), exception);
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now( ).plusHours( 2 ).toInstant( ZoneOffset.UTC );
    }
}
