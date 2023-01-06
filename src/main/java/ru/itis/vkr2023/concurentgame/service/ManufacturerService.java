package ru.itis.vkr2023.concurentgame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.dto.ManufacturerStatusInfo;
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

    public List<ManufacturerStatus> getManufacturerStatusListByGameStageId(Long id) {
        return manufacturerStatusRepository.findAllByGameStageId(id);
    }

    public List<ManufacturerStatus> getManufacturerStatusListByManufacturerId(Long id) {
        return manufacturerStatusRepository.findAllByManufacturerIdOrderById(id);
    }

    public List<ManufacturerStatusInfo> findAllInfoByManufacturerIdOrderById(Long id) {
        return manufacturerStatusRepository.findAllInfoByManufacturerIdOrderById(id);
    }

    public ManufacturerStatus getLastManufacturerStatusByManufacturerId(Long id) {
        List<ManufacturerStatus> lst = manufacturerStatusRepository.findAllByManufacturerIdOrderById(id);
        if (!lst.isEmpty()) {
            return lst.get(lst.size() - 1);
        }
        return null;
    }

    /**
     * Для этапа @param stage игры создаем ManufacturerStatus всех производителей
     *
     * @return список ManufacturerStatus для всех производителей
     */
    public List<ManufacturerStatus> makeManufactureStatusListForGameStage(GameStage stage) {

        List<Manufacturer> manufacturers = manufacturerRepository.findAllByGameId(stage.getGame().getId());

        manufacturers.forEach(manufacturer -> {
            ManufacturerStatus status = ManufacturerStatus.builder()
                    .manufacturer(manufacturer)
                    .gameStage(stage)
                    .advertisement(0d)
                    .income(0d)
                    .price(0d)
                    .productCount(0d)
                    .assortment(1)
                    .balance(calculateBalance(manufacturer))
                    .build();

            status = manufacturerStatusRepository.save(status);

            stage.getManufacturerStatusList().add(status);
        });

        return stage.getManufacturerStatusList();
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

    /** производителя
     * Обновление параметров этапа
     *
     * @param manufacturer
     * @param gameStage
     * @param manufacturerStatus - данные формы с параметрами этапа
     */
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
            //TODO - здесь необходимо создать ManufacturerStatus и рассчитать баланс
            ManufacturerStatus ms = ManufacturerStatus.builder().build().setAdvertisement(manufacturerStatus.getAdvertisement())
                    .setPrice(manufacturerStatus.getPrice())
                    .setAssortment(manufacturerStatus.getAssortment())
                    .setProductCount(manufacturerStatus.getProductCount()).setManufacturer(manufacturer)
                    .setGameStage(gameStage) .setBalance(calculateBalance(manufacturer));

            manufacturerStatusRepository.save(ms);
        }

        //else throw new IllegalArgumentException();
    }

    public Manufacturer findById(Long statusId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(statusId);
        return manufacturer.isPresent() ? manufacturer.get() : null;
    }

    public Game findGameByManufacturerId(Long id) {
        return manufacturerRepository.findGameByManufacturerId(id);
    }

    /**
     * Подсчет остатка средств производителя @param manufacturer на начало очередного кона - этапа игры
     * @return balance
     *
     * Алгоритм подсчета. На начало игры (1 этап) равен стартовому капиталу из параметров игры, для каждого последующего
     * этапа вычисляется по данным предыдущего как:
     * баланс_предыдущего_этапа - затраты_на_рекламу + выручка - расходы_на_производство_распроданного_товара
     *
     * расходы_на_производство_распроданного_товара = (выручка / цена) * себестоимость_единицы_товара
     *
     * себестоимость_единицы_товара зависит от количества произведенного товара с учетом ассортимента (т.е. кол-во/ассортимент)
     */
    public double calculateBalance(Manufacturer manufacturer) {
        List<ManufacturerStatus> statuses = manufacturerStatusRepository.findAllByManufacturerOrderById(manufacturer);
        System.out.println(statuses.size());
        int i = statuses.size();
        if (i ==0 || (!statuses.isEmpty() && statuses.size() == 1 && (statuses.get(0).getBalance() == null || statuses.get(0).getBalance() == 0 ))) {
            return manufacturer.getGame().getStartUpCapital();
        } else {

            return (statuses.get(i-1).getBalance() - statuses.get(i-1).getAdvertisement() +
                    statuses.get(i-1).getIncome() -
                    (statuses.get(i-1).getIncome() / statuses.get(i-1).getPrice())
                            * manufacturer.getGame().calculateCostPrice(statuses.get(i-1).getProductCount() / statuses.get(i-1).getAssortment()));
        }
    }

    public List<ManufacturerStatusInfo> findAllInfoByGameIdOrderById(Long gameId) {
        return manufacturerStatusRepository.findAllInfoByGameIdOrderById(gameId);
    }
}
