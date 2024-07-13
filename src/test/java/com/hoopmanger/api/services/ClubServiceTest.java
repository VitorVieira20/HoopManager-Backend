package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.club.Club;
import com.hoopmanger.api.domain.club.ClubRequestDTO;
import com.hoopmanger.api.domain.club.ClubUpdateRequestDTO;
import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.ClubRepository;
import com.hoopmanger.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ClubService clubService;

    private UUID clubId;
    private UUID ownerId;
    private UUID userId;
    private Club club;
    private List<Club> clubs;
    private ClubRequestDTO clubRequestDTO;
    private ClubUpdateRequestDTO clubUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clubId = UUID.randomUUID();
        ownerId = UUID.randomUUID();
        userId = UUID.randomUUID();

        club = new Club();
        club.setId(clubId);
        club.setOwner_id(ownerId);
        club.setName("Club Name");
        club.setEmail("email@example.com");
        club.setPhone(1234567890L);
        club.setInstagram("instagram");
        club.setTwitter("twitter");
        club.setFacebook("facebook");

        clubs = Collections.singletonList(club);

        clubRequestDTO = new ClubRequestDTO(ownerId, "New Club Name", "newemail@example.com", 9876543210L, "new_instagram", "new_twitter", "new_facebook");

        clubUpdateRequestDTO = new ClubUpdateRequestDTO("Updated Club Name", "updatedemail@example.com", 1122334455L, "updated_instagram", "updated_twitter", "updated_facebook");
    }

    @Test
    @DisplayName("Get Club by ID")
    void testGetClubById() {
        when(clubRepository.findClubById(clubId)).thenReturn(club);

        Club foundClub = clubService.getClubById(clubId);

        assertNotNull(foundClub);
        assertEquals("Club Name", foundClub.getName());
    }

    @Test
    @DisplayName("Get Clubs by Owner ID")
    void testGetClubsByOwnerId() {
        when(clubRepository.findClubsByOwnerId(ownerId)).thenReturn(clubs);

        List<Club> foundClubs = clubService.getClubsByOwnerId(ownerId);

        assertFalse(foundClubs.isEmpty());
        assertEquals(1, foundClubs.size());
        assertEquals("Club Name", foundClubs.get(0).getName());
    }

    @Test
    @DisplayName("Get Clubs by Name")
    void testGetClubsByName() {
        when(clubRepository.findClubsByName("Club Name")).thenReturn(clubs);

        List<Club> foundClubs = clubService.getClubsByName("Club Name");

        assertFalse(foundClubs.isEmpty());
        assertEquals(1, foundClubs.size());
        assertEquals("Club Name", foundClubs.get(0).getName());
    }

    @Test
    @DisplayName("Get User Favorite Clubs by User ID")
    void testGetUserFavoriteClubsByUserId() {
        User user = new User();
        user.setId(userId);
        user.setClubs(Collections.singletonList(clubId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(clubRepository.findUserFavoriteClubsByIds(user.getClubs())).thenReturn(clubs);

        List<Club> foundClubs = clubService.getUserFavoriteClubsByUserId(userId);

        assertFalse(foundClubs.isEmpty());
        assertEquals(1, foundClubs.size());
        assertEquals("Club Name", foundClubs.get(0).getName());
    }

    @Test
    @DisplayName("User Not Found for Favorite Clubs")
    void testGetUserFavoriteClubsByUserIdNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        List<Club> foundClubs = clubService.getUserFavoriteClubsByUserId(userId);

        assertTrue(foundClubs.isEmpty());
    }

    @Test
    @DisplayName("Create Club")
    void testCreateClub() {
        when(clubRepository.save(any(Club.class))).thenReturn(club);

        Club createdClub = clubService.createClub(clubRequestDTO);

        assertNotNull(createdClub);
        assertEquals("Club Name", createdClub.getName());
    }

    @Test
    @DisplayName("Update Club")
    void testUpdateClub() {
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(clubRepository.save(any(Club.class))).thenReturn(club);

        Club updatedClub = clubService.updateClub(clubId, clubUpdateRequestDTO);

        assertNotNull(updatedClub);
        assertEquals("Updated Club Name", updatedClub.getName());
    }

    @Test
    @DisplayName("Club Not Found while updating")
    void testUpdateClubNotFound() {
        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        Club updatedClub = clubService.updateClub(clubId, clubUpdateRequestDTO);

        assertNull(updatedClub);
    }

    @Test
    @DisplayName("Delete Club")
    void testDeleteClub() {
        when(clubRepository.existsById(clubId)).thenReturn(true);
        doNothing().when(clubRepository).deleteById(clubId);

        boolean deleted = clubService.deleteClub(clubId);

        assertTrue(deleted);
        verify(clubRepository, times(1)).deleteById(clubId);
    }

    @Test
    @DisplayName("Club Not Found while deleting")
    void testDeleteClubNotFound() {
        when(clubRepository.existsById(clubId)).thenReturn(false);

        boolean deleted = clubService.deleteClub(clubId);

        assertFalse(deleted);
        verify(clubRepository, never()).deleteById(clubId);
    }
}
