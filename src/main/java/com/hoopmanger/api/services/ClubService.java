package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.ClubRepository;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    public Club getClubById( UUID clubId ) {
        return clubRepository.findClubById( clubId );
    }
    public List<Club> getClubsByOwnerId( UUID ownerId ) {
        return clubRepository.findClubsByOwnerId( ownerId );
    }
    public List<Club> getClubsByName( String clubName ) {
        return clubRepository.findClubsByName( clubName );
    }
    public List<Club> getUserFavoriteClubsByUserId( UUID userId ) {
        User user = userRepository.findById( userId ).orElse( null );
        if ( user != null && user.getClubs( ) != null ) {
            return clubRepository.findUserFavoriteClubsByIds( user.getClubs( ) );
        } else {
            return List.of( );
        }
    }

    public Club createClub( ClubRequestDTO clubRequestDTO ) {
        Club club = new Club ( );
        club.setOwner_id( clubRequestDTO.owner_id( ) );
        club.setName( clubRequestDTO.name( ) );
        club.setEmail( clubRequestDTO.email( ) );
        club.setPhone( clubRequestDTO.phone( ) );
        club.setInstagram( clubRequestDTO.instagram( ) );
        club.setTwitter( clubRequestDTO.twitter( ) );
        club.setFacebook( clubRequestDTO.facebook(  ));
        return clubRepository.save( club );
    }

    public Club updateClub( UUID clubId, ClubUpdateRequestDTO clubUpdateRequestDTO ) {
        Club club = clubRepository.findById( clubId ).orElse( null );
        if (club == null) {
            return null;
        }
        club.setName( clubUpdateRequestDTO.name( ) );
        club.setEmail( clubUpdateRequestDTO.email( ) );
        club.setPhone( clubUpdateRequestDTO.phone( ) );
        club.setInstagram( clubUpdateRequestDTO.instagram( ) );
        club.setTwitter( clubUpdateRequestDTO.twitter( ) );
        club.setFacebook( clubUpdateRequestDTO.facebook( ) );
        return clubRepository.save( club );
    }

    public boolean deleteClub( UUID clubId ) {
        if (clubRepository.existsById( clubId ) ) {
            clubRepository.deleteById( clubId );
            return true;
        } else {
            return false;
        }
    }
}
