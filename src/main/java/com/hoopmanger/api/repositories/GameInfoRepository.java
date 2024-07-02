package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.game.Game;
import com.hoopmanger.api.domain.gameInfo.GameInfo;
import com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO;
import com.hoopmanger.api.domain.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameInfoRepository extends JpaRepository<GameInfo, Integer> {

    @Query("SELECT new com.hoopmanger.api.domain.gameInfo.GameInfoWithPlayerNameResponseDTO(g.id, g.player_id, g.points, g.assists, g.rebounds, g.game_id, p.name) " +
            "FROM GameInfo g JOIN Player p ON g.player_id = p.id WHERE g.game_id = :gameId")
    List<GameInfoWithPlayerNameResponseDTO> findGameInfoWithPlayerNameByGameId(@Param("gameId") UUID gameId);
}
