package com.hoopmanger.api.controllers;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/club" )
public class ClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping( "/{clubId}" )
    public ResponseEntity<Club> getClubById( @PathVariable UUID clubId ) {
        Club club = clubService.getClubById( clubId );
        if ( club == null ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( club );
        }
    }

    @GetMapping( "/owner/{ownerId}" )
    public ResponseEntity<List<Club>> getClubsByOwnerId( @PathVariable UUID ownerId ) {
        List<Club> clubs = clubService.getClubsByOwnerId( ownerId );
        if ( clubs.isEmpty( ) ) {
            return ResponseEntity.noContent( ).build( );
        } else {
            return ResponseEntity.ok( clubs );
        }
    }

    @PostMapping( "/" )
    public ResponseEntity<Club> createClub( @Valid @RequestBody ClubRequestDTO clubRequestDTO ) {
        Club createdClub = clubService.createClub( clubRequestDTO );
        return ResponseEntity.ok( createdClub );
    }

    @PutMapping( "/{clubId}" )
    public ResponseEntity<Club> updateClub( @PathVariable UUID clubId, @Valid @RequestBody ClubUpdateRequestDTO clubUpdateRequestDTO) {
        Club updatedClub = clubService.updateClub( clubId, clubUpdateRequestDTO );
        if ( updatedClub == null ) {
            return ResponseEntity.notFound( ).build( );
        } else {
            return ResponseEntity.ok( updatedClub );
        }
    }
}
