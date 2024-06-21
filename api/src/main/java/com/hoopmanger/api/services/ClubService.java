package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    public Club getClubById( UUID clubId ) {
        return clubRepository.findClubById( clubId );
    }

    public List<Club> getClubsByOwnerId( UUID ownerId ) {
        return clubRepository.findClubsByOwnerId( ownerId );
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
}
