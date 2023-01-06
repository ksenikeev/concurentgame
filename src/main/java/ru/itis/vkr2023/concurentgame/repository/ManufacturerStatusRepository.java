package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vkr2023.concurentgame.dto.ManufacturerStatusInfo;
import ru.itis.vkr2023.concurentgame.model.Game;
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

    @Query("select s from ManufacturerStatus s where s.manufacturer.id = :id order by s.id")
    List<ManufacturerStatus> findAllByManufacturerIdOrderById(@Param("id") Long id);

    @Query("select new ru.itis.vkr2023.concurentgame.dto.ManufacturerStatusInfo(" +
            "s.gameStage.startDate, s.productCount, s.price, s.advertisement, s.assortment, s.income, s.balance) " +
            "from ManufacturerStatus s where s.manufacturer.id = :id order by s.id")
    List<ManufacturerStatusInfo> findAllInfoByManufacturerIdOrderById(@Param("id") Long id);

    @Query("select new ru.itis.vkr2023.concurentgame.dto.ManufacturerStatusInfo(" +
            "s.gameStage.startDate, s.productCount, s.price, s.advertisement, s.assortment, s.income, s.balance, s.manufacturer.name) " +
            "from ManufacturerStatus s where s.gameStage.game.id = :id order by s.manufacturer.id, s.id")
    List<ManufacturerStatusInfo> findAllInfoByGameIdOrderById(@Param("id") Long id);
}
