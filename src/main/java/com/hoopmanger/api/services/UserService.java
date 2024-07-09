package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.domain.user.UserUpdateClubsRequestDTO;
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
                .map( user -> new UserResponseDTO(
                        user.getId( ),
                        user.getName( ),
                        user.getEmail( ),
                        user.getRole( ),
                        user.getPlan( ),
                        user.getClubs( ),
                        user.getTeams( ),
                        user.getGames( ),
                        user.getAthletes( )
                )
            );
    }


    public Optional<UserResponseDTO> updateUser( UUID userId, UserUpdateClubsRequestDTO userUpdateClubsRequestDTO ) {
        User user = userRepository.findById( userId ).orElse( null );
        if ( user == null ) {
            return Optional.empty( );
        }
        user.setClubs( userUpdateClubsRequestDTO.clubs( ) );
        user = userRepository.save( user );
        return Optional.of( new UserResponseDTO(
                user.getId( ),
                user.getName( ),
                user.getEmail( ),
                user.getRole( ),
                user.getPlan( ),
                user.getClubs( ),
                user.getTeams( ),
                user.getGames( ),
                user.getAthletes( )
        ) );
    }
}
