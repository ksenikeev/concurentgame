package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStage;

import java.util.List;
import java.util.Optional;

public interface GameStageRepository extends JpaRepository<GameStage, Long> {
    Optional<GameStage> findByGameIdAndEndDateIsNull(Long id);

    //Optional<GameStage> findByGameId(Long id);

    List<GameStage> findByGameOrderByIdDesc(Game game);

}
