package ru.itis.vkr2023.concurentgame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.Manufacturer;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;
import ru.itis.vkr2023.concurentgame.repository.ManufacturerRepository;
import ru.itis.vkr2023.concurentgame.repository.ManufacturerStatusRepository;
import ru.itis.vkr2023.concurentgame.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerStatusRepository manufacturerStatusRepository;
    private final ManufacturerRepository manufacturerRepository;

    public List<ManufacturerStatus> getManufactureStatusListByGameStageId(Long id) {
        return manufacturerStatusRepository.findAllByGameStageId(id);
    }

    public List<Manufacturer> getManufacturersByGameId(Long id) {
        return manufacturerRepository.findAllByGameId(id);
    }

    public ManufacturerStatus joinGame(UserDetailsImpl userDetails, Game game, String name) {
        Manufacturer manufacturer = manufacturerRepository.save(Manufacturer.builder()
                .game(game)
                .user(userDetails.getUser())
                .name(name)
                .build());

        return manufacturerStatusRepository.save(ManufacturerStatus.builder()
                .manufacturer(manufacturer)
                .build());

    }

    public void enterParameters(Long statusId, ManufacturerStatus manufacturerStatus) {
        Optional<ManufacturerStatus> status = manufacturerStatusRepository.findById(statusId);
        if (status.isPresent()) {
            manufacturerStatusRepository.save(status.get()
                    .setAdvertisement(manufacturerStatus.getAdvertisement())
                    .setPrice(manufacturerStatus.getPrice())
                    .setAssortment(manufacturerStatus.getAssortment())
                    .setProductCount(manufacturerStatus.getProductCount()));
        } else throw new IllegalArgumentException();
    }
}
