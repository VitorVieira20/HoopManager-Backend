package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
