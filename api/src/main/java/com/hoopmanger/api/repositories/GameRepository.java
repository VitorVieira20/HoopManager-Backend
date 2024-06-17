package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
