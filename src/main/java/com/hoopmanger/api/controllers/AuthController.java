package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.auth.LoginRequestDTO;
import com.hoopmanger.api.domain.user.auth.RegisterRequestDTO;
import com.hoopmanger.api.domain.user.auth.ResponseDTO;
import com.hoopmanger.api.infra.security.TokenService;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping( "/api/auth" )
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;

    @PostMapping( "/login" )
    public ResponseEntity login( @RequestBody LoginRequestDTO body ){
        User user = this.userRepository.findByEmail( body.email( ) ).orElseThrow( ( ) -> new RuntimeException( "User not found" ) );
        if( passwordEncoder.matches( body.password( ), user.getPassword( ) ) ) {
            String token = this.tokenService.generateToken( user );
            return ResponseEntity.ok( new ResponseDTO(
                    user.getName( ),
                    user.getEmail( ),
                    user.getId( ),
                    user.getRole( ),
                    user.getPlan( ),
                    user.getClubs( ),
                    user.getTeams( ),
                    user.getGames( ),
                    user.getAthletes( ),
                    token
            ));
        }
        return ResponseEntity.badRequest( ).build( );
    }


    @PostMapping( "/register" )
    public ResponseEntity register( @RequestBody RegisterRequestDTO body ){
        Optional<User> user = this.userRepository.findByEmail( body.email( ) );

        if( user.isEmpty( ) ) {
            User newUser = new User( );
            newUser.setPassword( passwordEncoder.encode( body.password( ) ) );
            newUser.setEmail( body.email( ) );
            newUser.setName( body.name( ) );
            newUser.setRole( body.role( ) );
            newUser.setPlan( body.plan( ) );
            newUser.setClubs( new ArrayList<>() );
            newUser.setTeams( new ArrayList<>() );
            newUser.setGames( new ArrayList<>() );
            newUser.setAthletes( new ArrayList<>() );
            this.userRepository.save( newUser );

            String token = this.tokenService.generateToken( newUser );
            return ResponseEntity.ok( new ResponseDTO(
                    newUser.getName( ),
                    newUser.getEmail( ),
                    newUser.getId( ),
                    newUser.getRole( ),
                    newUser.getPlan( ),
                    newUser.getClubs( ),
                    newUser.getTeams( ),
                    newUser.getGames( ),
                    newUser.getAthletes( ),
                    token
            ) );
        }
        return ResponseEntity.badRequest().build();
    }
}
