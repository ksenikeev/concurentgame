package ru.itis.vkr2023.concurentgame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStage;
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

/*
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
*/

    public Manufacturer joinGame(UserDetailsImpl userDetails, Game game, String name) {
        return manufacturerRepository.save(Manufacturer.builder()
                .game(game)
                .user(userDetails.getUser())
                .name(name)
                .build());
    }

    public Manufacturer findByGameIdAndUserId(Long gameId, Long userId) {
        return manufacturerRepository.findByGameIdAndUserId(gameId, userId);
    }

    @Transactional
    public void enterParameters(Manufacturer manufacturer, GameStage gameStage, ManufacturerStatus manufacturerStatus) {
        Optional<ManufacturerStatus> status = manufacturerStatusRepository.findByGameStageAndManufacturer(gameStage, manufacturer);
        if (status.isPresent()) {
            ManufacturerStatus ms = status.get();
            manufacturerStatusRepository.save(ms
                    .setAdvertisement(manufacturerStatus.getAdvertisement())
                    .setPrice(manufacturerStatus.getPrice())
                    .setAssortment(manufacturerStatus.getAssortment())
                    .setProductCount(manufacturerStatus.getProductCount())
                    .setBalance(ms.getBalance() == null ? 0d : ms.getBalance()));
        } else {

            ManufacturerStatus ms = ManufacturerStatus.builder().build().setAdvertisement(manufacturerStatus.getAdvertisement())
                    .setPrice(manufacturerStatus.getPrice())
                    .setAssortment(manufacturerStatus.getAssortment())
                    .setProductCount(manufacturerStatus.getProductCount()).setManufacturer(manufacturer)
                    .setGameStage(gameStage) .setBalance(0d);
            ms = manufacturerStatusRepository.save(ms);
        }
        calculateBalance(manufacturer);

        //else throw new IllegalArgumentException();
    }

    public Manufacturer findById(Long statusId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(statusId);
        return manufacturer.isPresent() ? manufacturer.get() : null;
    }

    public void calculateBalance(Manufacturer manufacturer) {
        List<ManufacturerStatus> statuses = manufacturerStatusRepository.findAllByManufacturerOrderById(manufacturer);

        int i = statuses.size() - 1;
        if (!statuses.isEmpty() && statuses.size() == 1 && statuses.get(0).getBalance() == 0) {
            statuses.get(0).setBalance(manufacturer.getGame().getStartUpCapital());
        } else {
            statuses.get(i).setBalance(statuses.get(i-1).getBalance() - statuses.get(i-1).getAdvertisement() +
                    statuses.get(i-1).getIncome() -
                    (statuses.get(i-1).getIncome() / statuses.get(i-1).getPrice())
                            * manufacturer.getGame().calculateCostPrice(statuses.get(i-1).getProductCount() / statuses.get(i-1).getAdvertisement()));
        }
        manufacturerStatusRepository.save(statuses.get(i));
    }

}
