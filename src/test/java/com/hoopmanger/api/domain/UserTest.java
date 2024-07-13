package com.hoopmanger.api.domain;

import com.hoopmanger.api.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Test
    @DisplayName( "User All Args Constructor Test" )
    public void testUserAllArgsConstructor( ) {
        UUID userId = UUID.randomUUID( );
        String userName = "User Name";
        String userEmail = "User Email";
        String userPassword = "User Password";
        String role = "Role";
        String plan = "Plan";
        List<UUID> clubs = new ArrayList<UUID>();
        List<UUID> teams = new ArrayList<UUID>();
        List<UUID> games = new ArrayList<UUID>();
        List<UUID> athletes = new ArrayList<UUID>();

        User user = new User( userId, userName, userEmail, userPassword, role, plan, clubs, teams, games, athletes );

        assertAll(
                ( ) -> assertEquals( userId, user.getId( ) ),
                ( ) -> assertEquals( userName, user.getName( ) ),
                ( ) -> assertEquals( userEmail, user.getEmail( ) ),
                ( ) -> assertEquals( userPassword, user.getPassword( ) ),
                ( ) -> assertEquals( role, user.getRole( ) ),
                ( ) -> assertEquals( plan, user.getPlan( ) ),
                ( ) -> assertEquals( clubs, user.getClubs( ) ),
                ( ) -> assertEquals( teams, user.getTeams( ) ),
                ( ) -> assertEquals( games, user.getGames( ) ),
                ( ) -> assertEquals( athletes, user.getAthletes( ) )
        );
    }
}
