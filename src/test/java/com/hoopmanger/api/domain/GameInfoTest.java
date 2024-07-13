package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class GameInfoTest {

    @Test
    @DisplayName( "Game Info All Args Constructor Test" )
    public void testGameInfoAllArgsConstructor( ) {
        UUID playerId = UUID.randomUUID( );
        UUID gameId = UUID.randomUUID( );
        int points = 20;
        int assists = 5;
        int rebounds = 10;

        GameInfo gameInfo = new GameInfo( 1, playerId, points, assists, rebounds, gameId );

        assertAll(
                ( ) -> assertEquals( 1, gameInfo.getId( ) ),
                ( ) -> assertEquals( playerId, gameInfo.getPlayer_id( ) ),
                ( ) -> assertEquals( points, gameInfo.getPoints( ) ),
                ( ) -> assertEquals( assists, gameInfo.getAssists( ) ),
                ( ) -> assertEquals( rebounds, gameInfo.getRebounds( ) ),
                ( ) -> assertEquals( gameId, gameInfo.getGame_id( ) )
        );
    }

    @Test
    @DisplayName( "Game Info With Player Name Response All Args Constructor Test" )
    public void testGameInfoWithNameResponseAllArgsConstructor( ) {
        UUID playerId = UUID.randomUUID( );
        UUID gameId = UUID.randomUUID( );
        int points = 20;
        int assists = 5;
        int rebounds = 10;
        String playerName = "Player Name";

        GameInfoWithPlayerNameResponseDTO gameInfoWithPlayerNameResponseDTO = new GameInfoWithPlayerNameResponseDTO( 1, playerId, points, assists, rebounds, gameId, playerName );

        assertAll(
                ( ) -> assertEquals( 1, gameInfoWithPlayerNameResponseDTO.getId( ) ),
                ( ) -> assertEquals( playerId, gameInfoWithPlayerNameResponseDTO.getPlayer_id( ) ),
                ( ) -> assertEquals( points, gameInfoWithPlayerNameResponseDTO.getPoints( ) ),
                ( ) -> assertEquals( assists, gameInfoWithPlayerNameResponseDTO.getAssists( ) ),
                ( ) -> assertEquals( rebounds, gameInfoWithPlayerNameResponseDTO.getRebounds( ) ),
                ( ) -> assertEquals( gameId, gameInfoWithPlayerNameResponseDTO.getGame_id( ) ),
                ( ) -> assertEquals( playerName, gameInfoWithPlayerNameResponseDTO.getPlayer_name( ) )
        );
    }
}
