package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.domain.user.UserUpdateRequestDTO;
import com.hoopmanger.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping( "/api/user" )
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById( @PathVariable UUID id ) {
        Optional<UserResponseDTO> user = userService.getUserById( id );
        return user.map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser( @PathVariable UUID userId, @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO ) {
        Optional<UserResponseDTO> updatedUser = userService.updateUser( userId, userUpdateRequestDTO );
        return updatedUser.map( ResponseEntity::ok )
                .orElseGet( ( ) -> ResponseEntity.notFound( ).build( ) );
    }
}
