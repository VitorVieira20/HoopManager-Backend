package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.club.Club;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubTest {
    @Test
    @DisplayName( "Club All Args Constructor Test" )
    public void testClubAllArgsConstructor( ) {
        UUID clubId = UUID.randomUUID( );
        UUID ownerId = UUID.randomUUID( );
        String clubName = "Club Name";
        String clubEmail = "Club Email";
        int clubPhone = 925139873;
        String clubInstagram = "Instagram";
        String clubTwitter = "Twitter";
        String clubFacebook = "Facebook";

        Club club = new Club( clubId, clubPhone, clubName, clubEmail, clubInstagram, clubFacebook, clubTwitter, ownerId );

        assertAll(
                ( ) -> assertEquals( clubId, club.getId( ) ),
                ( ) -> assertEquals( clubPhone, club.getPhone( ) ),
                ( ) -> assertEquals( clubName, club.getName( ) ),
                ( ) -> assertEquals( clubEmail, club.getEmail( ) ),
                ( ) -> assertEquals( clubInstagram, club.getInstagram( ) ),
                ( ) -> assertEquals( clubTwitter, club.getTwitter( ) ),
                ( ) -> assertEquals( clubFacebook, club.getFacebook( ) ),
                ( ) -> assertEquals( ownerId, club.getOwner_id( ) )
        );
    }
}
