package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;

import java.util.List;

public interface ManufacturerStatusRepository extends JpaRepository<ManufacturerStatus, Long> {
    List<ManufacturerStatus> findAllByGameStageId(Long id);
}
