package ru.itis.vkr2023.concurentgame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.model.*;
import ru.itis.vkr2023.concurentgame.model.security.User;
import ru.itis.vkr2023.concurentgame.repository.BuyerRepository;
import ru.itis.vkr2023.concurentgame.repository.GameRepository;
import ru.itis.vkr2023.concurentgame.repository.GameStageRepository;
import ru.itis.vkr2023.concurentgame.repository.ManufacturerStatusRepository;
import ru.itis.vkr2023.concurentgame.service.buyer.StageService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static ru.itis.vkr2023.concurentgame.model.GameStatus.created;
import static ru.itis.vkr2023.concurentgame.model.GameStatus.gameover;
import static ru.itis.vkr2023.concurentgame.model.GameStatus.stageover;
import static ru.itis.vkr2023.concurentgame.model.GameStatus.stagestarted;

@Service
@RequiredArgsConstructor
public class GameService {

    private final ManufacturerService manufacturerService;
    private final GameRepository gameRepository;
    private final GameStageRepository gameStageRepository;

    private final ManufacturerStatusRepository manufacturerStatusRepository;
    private final BuyerRepository buyerRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game findActiveUserGame(User user) {
        List<Game> games = gameRepository.findGameByUserNotInGameStatus(user, gameover);

        return !games.isEmpty() ? games.get(0) : null;
    }

    public void createGame(Game game) {

        if (existActiveGame()) {
            throw new IllegalStateException("Уже существует активнаяя игра! Невозможно создать новую.");
        }

        gameRepository.save(Game.builder()
                .gameStatus(GameStatus.created)
                .buyersBudget(game.getBuyersBudget())
                .costPrice(game.getCostPrice())
                .startUpCapital(game.getStartUpCapital())
                .startDate(new Date())
                .build());
    }

    private boolean existActiveGame() {
        return !gameRepository.findAllByGameStatusNot(GameStatus.gameover).isEmpty();
    }

    public Game getGameById(Long id) {
        return gameRepository.getById(id);
    }

    public List<GameStage> getGameStageByGame(Game game) {
        return gameStageRepository.findByGameOrderByIdDesc(game);
    }

    public void startGameStage(Long id) {

        Game game = getGameById(id);

        if (stagestarted.equals(game.getGameStatus())) {
            throw new IllegalStateException("Невозможно запустить этап, пока не будет завершен предыдущий");
        } else if (gameover.equals(game.getGameStatus())) {
            throw new IllegalStateException("Данная игра уже завершена");
        }

        game.setGameStatus(stagestarted);
        //gameRepository.save(game);

        GameStage stage = GameStage.builder()
                .game(game)
                .startDate(new Date())
                .manufacturerStatusList(new ArrayList<>())
                .build();

        gameStageRepository.save(stage);

        // Создаем для каждого производителя экземпляр ManufacturerStatus
        stage.setManufacturerStatusList(manufacturerService.makeManufactureStatusListForGameStage(stage));
    }

    @Transactional
    public Game stopGameStage(Long id) {
        Game game = getGameById(id);
        game.setGameStatus(GameStatus.stageover);
        gameRepository.save(game);
        GameStage stage = gameStageRepository.findByGameIdAndEndDateIsNull(game.getId())
                .orElseThrow(() -> new IllegalStateException("Не найден текущий шаг игры"));
        stage.setEndDate(new Date());
        gameStageRepository.save(stage);

        //вызов логики покупателя
        List<Buyer> buyers = buyerRepository.findAll();
        List<ManufacturerStatus> manufacturerStatuses = manufacturerService.getManufacturerStatusListByGameStageId(stage.getId());

        StageService stageService = new StageService(manufacturerStatusRepository);
        stageService.calculateStage(buyers, manufacturerStatuses, game);

        for (ManufacturerStatus ms : manufacturerStatuses) {
            manufacturerStatusRepository.save(ms);
        }

        return game;
    }

    public Optional<Game> getCurrentGame() {
        return gameRepository.findByGameStatusIn(List.of(created, stagestarted, stageover));
    }

    public void finishGame(Long id) {
        gameRepository.save(
                getGameById(id)
                        .setGameStatus(GameStatus.gameover));
        //TODO: подсчет итогов игры??
    }
}
