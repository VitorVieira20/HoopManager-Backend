package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club, UUID> {
}
