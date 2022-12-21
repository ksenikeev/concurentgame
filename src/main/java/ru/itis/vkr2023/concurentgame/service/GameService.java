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

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class GameService {

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

    public void startGameStage(Long id) {
        Game game = getGameById(id);
        game.setGameStatus(GameStatus.stagestarted);
        gameRepository.save(game);
        GameStage stage = GameStage.builder()
                .game(game)
                .startDate(new Date())
                .build();
        gameStageRepository.save(stage);

        //todo: вызов логики покупателя
        //todo: получение списка производителей
    }

    public void stopGameStage(Long id) {
        Game game = getGameById(id);
        game.setGameStatus(GameStatus.stageover);
        gameRepository.save(game);
        //todo: просавить enddate у stage
    }

    public void finishGame(Long id) {
        gameRepository.save(
                getGameById(id)
                        .setGameStatus(GameStatus.gameover));
        //todo: подсчет итогов игры??

    }
}
