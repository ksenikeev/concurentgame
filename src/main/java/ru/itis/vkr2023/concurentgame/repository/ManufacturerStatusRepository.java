package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vkr2023.concurentgame.model.GameStage;
import ru.itis.vkr2023.concurentgame.model.Manufacturer;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;

import java.util.List;
import java.util.Optional;

public interface ManufacturerStatusRepository extends JpaRepository<ManufacturerStatus, Long> {
    List<ManufacturerStatus> findAllByGameStageId(Long id);
    Optional<ManufacturerStatus> findByGameStageAndManufacturer(GameStage gameStage, Manufacturer manufacturer);

    @Query("select s from ManufacturerStatus s where s.manufacturer = :manufacturer order by s.id")
    List<ManufacturerStatus> findAllByManufacturerOrderById(@Param("manufacturer") Manufacturer manufacturer);
}
