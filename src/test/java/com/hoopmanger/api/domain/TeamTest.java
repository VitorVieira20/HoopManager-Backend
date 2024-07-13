package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamTest {

    @Test
    @DisplayName( "Team All Args Constructor Test" )
    public void testTeamAllArgsConstructor( ) {
        UUID teamId = UUID.randomUUID( );
        UUID clubId = UUID.randomUUID( );
        String teamName = "Team Name";

        Team team = new Team( teamId, teamName, clubId );

        assertAll(
                ( ) -> assertEquals( teamId, team.getId( ) ),
                ( ) -> assertEquals( teamName, team.getName( ) ),
                ( ) -> assertEquals( clubId, team.getClub_id( ) )
        );
    }
}
