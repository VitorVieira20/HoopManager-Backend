package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.domain.user.UserResponseDTO;
import com.hoopmanger.api.domain.user.UserUpdateRequestDTO;
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


    public Optional<UserResponseDTO> updateUser( UUID userId, UserUpdateRequestDTO userUpdateClubsRequestDTO ) {
        User user = userRepository.findById( userId ).orElse( null );
        if ( user == null ) {
            return Optional.empty( );
        }

        if ( userUpdateClubsRequestDTO.clubs( ) != null && !userUpdateClubsRequestDTO.clubs( ).isEmpty( ) ) {
            user.setClubs( userUpdateClubsRequestDTO.clubs( ) );
        }

        if ( userUpdateClubsRequestDTO.teams( ) != null && !userUpdateClubsRequestDTO.teams( ).isEmpty( ) ) {
            user.setTeams( userUpdateClubsRequestDTO.teams( ) );
        }

        if ( userUpdateClubsRequestDTO.players( ) != null && !userUpdateClubsRequestDTO.players( ).isEmpty( ) ) {
            user.setAthletes( userUpdateClubsRequestDTO.players( ) );
        }

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
