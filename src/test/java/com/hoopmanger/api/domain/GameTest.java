package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.game.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class GameTest {

    @Test
    @DisplayName( "Game All Args Constructor Test" )
    public void testGameAllArgsConstructor( ) {
        UUID gameId = UUID.randomUUID( );
        UUID teamId = UUID.randomUUID( );
        Date gameDate = new Date( );
        String gameHomeTeam = "Home Team";
        String gameAwayTeam = "Away Team";
        int gameHomeScore = 100;
        int gameAwayScore = 90;
        String gameLocation = "Game Location";

        Game game = new Game( gameId, gameDate, gameHomeTeam, gameAwayTeam, gameHomeScore, gameAwayScore, gameLocation, teamId );

        assertAll(
                ( ) -> assertEquals( gameId, game.getId( ) ),
                ( ) -> assertEquals( gameDate, game.getDate( ) ),
                ( ) -> assertEquals( gameHomeTeam, game.getHome_team( ) ),
                ( ) -> assertEquals( gameAwayTeam, game.getAway_team( ) ),
                ( ) -> assertEquals( gameHomeScore, game.getHome_score( ) ),
                ( ) -> assertEquals( gameAwayScore, game.getAway_score( ) ),
                ( ) -> assertEquals( gameLocation, game.getLocation( ) ),
                ( ) -> assertEquals( teamId, game.getTeam_id( ) )
        );
    }
}
