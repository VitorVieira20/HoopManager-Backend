package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
