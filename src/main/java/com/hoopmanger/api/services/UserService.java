package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<UserResponseDTO> getUserById( UUID id ) {
        return userRepository.findById( id )
                .map( user -> new UserResponseDTO( user.getId( ), user.getName( ), user.getEmail( ) ) );
    }
}
