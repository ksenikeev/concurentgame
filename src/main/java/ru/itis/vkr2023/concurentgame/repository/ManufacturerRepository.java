package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vkr2023.concurentgame.model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findAllByGameId(Long id);

    @Query("select m from Manufacturer m where m.game.id = :gameId and m.user.id = :userId ")
    Manufacturer findByGameIdAndUserId(@Param("gameId") Long gameId, @Param("userId") Long userId);
}
