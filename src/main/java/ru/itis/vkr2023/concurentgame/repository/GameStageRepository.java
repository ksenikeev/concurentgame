package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vkr2023.concurentgame.model.GameStage;

public interface GameStageRepository extends JpaRepository<GameStage, Long> {
}
