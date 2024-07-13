package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerTest {

    @Test
    @DisplayName( "Player All Args Constructor Test" )
    public void testPlayerAllArgsConstructor( ) {
        UUID playerId = UUID.randomUUID( );
        UUID teamId = UUID.randomUUID( );
        String playerName = "Player Name";
        String playerPosition = "Player Position";

        Player player = new Player( playerId, playerName, playerPosition, teamId );

        assertAll(
                ( ) -> assertEquals( playerId, player.getId( ) ),
                ( ) -> assertEquals( playerName, player.getName( ) ),
                ( ) -> assertEquals( playerPosition, player.getPosition( ) ),
                ( ) -> assertEquals( teamId, player.getTeam_id( ) )
        );
    }
}
