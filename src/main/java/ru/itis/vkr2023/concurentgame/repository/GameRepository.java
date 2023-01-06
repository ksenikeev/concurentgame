package ru.itis.vkr2023.concurentgame.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStatus;
import ru.itis.vkr2023.concurentgame.model.security.User;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByGameStatusNot(GameStatus gameStatus);
    Optional<Game> findByGameStatusIn(List<GameStatus> statuses);

    @Query("select g from Manufacturer m join m.game g where m.user = :user  and g.gameStatus <> :status ")
    List<Game> findGameByUserNotInGameStatus(@Param("user") User user, @Param("status") GameStatus gameStatus);

    List<Game> findAllByGameStatusOrderByIdDesc(GameStatus gameStatus);
}
