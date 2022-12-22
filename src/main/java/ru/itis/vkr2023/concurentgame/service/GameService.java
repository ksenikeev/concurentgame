package ru.itis.vkr2023.concurentgame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStage;
import ru.itis.vkr2023.concurentgame.model.GameStatus;
import ru.itis.vkr2023.concurentgame.repository.GameRepository;
import ru.itis.vkr2023.concurentgame.repository.GameStageRepository;

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

    public List<Game> getAllGames() {
        return gameRepository.findAll();
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

    public Optional<GameStage> getGameStageByGameId(Long id) {
        return gameStageRepository.findByGameId(id);
    }

    public void startGameStage(Long id) {
        Game game = getGameById(id);
        if (stagestarted.equals(game.getGameStatus())) {
            throw new IllegalStateException("Невозможно запустить этап, пока не будет завершен предыдущий");
        } else if (gameover.equals(game.getGameStatus())) {
            throw new IllegalStateException("Данная игра уже завершена");
        }
        game.setGameStatus(stagestarted);
        gameRepository.save(game);
        GameStage stage = GameStage.builder()
                .game(game)
                .startDate(new Date())
                .build();
        gameStageRepository.save(stage);
        stage.setManufacturerStatusList(manufacturerService.getManufactureStatusListByGameStageId(game.getId()));
    }

    public Game stopGameStage(Long id) {
        Game game = getGameById(id);
        game.setGameStatus(GameStatus.stageover);
        gameRepository.save(game);
        GameStage stage = gameStageRepository.findByGameIdAndEndDateIsNull(game.getId())
                .orElseThrow(() -> new IllegalStateException("Не найден текущий шаг игры"));
        stage.setEndDate(new Date());
        gameStageRepository.save(stage);
        //todo: вызов логики покупателя
        return game;
    }

    public Optional<Game> getCurrentGame() {
        return gameRepository.findByGameStatusIn(List.of(created, stagestarted, stageover));
    }

    public void finishGame(Long id) {
        gameRepository.save(
                getGameById(id)
                        .setGameStatus(GameStatus.gameover));
        //todo: подсчет итогов игры??

    }
}
