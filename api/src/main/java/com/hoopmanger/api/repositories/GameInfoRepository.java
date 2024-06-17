package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.gameInfo.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepository extends JpaRepository<GameInfo, Integer> {
}
