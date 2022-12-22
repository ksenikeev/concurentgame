package ru.itis.vkr2023.concurentgame.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStatus;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByGameStatusNot(GameStatus gameStatus);
    Optional<Game> findByGameStatusIn(List<GameStatus> statuses);
}
