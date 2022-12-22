package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vkr2023.concurentgame.model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findAllByGameId(Long id);
}
