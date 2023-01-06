package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStatus;
import ru.itis.vkr2023.concurentgame.service.GameService;
import ru.itis.vkr2023.concurentgame.service.ManufacturerService;

@Controller
@RequiredArgsConstructor
public class GameController {

    private  final GameService gameService;
    private final ManufacturerService manufacturerService;

    @GetMapping("/create")
    public String getCreateGamePage() {
        return "create_game";
    }

    @PostMapping("/create")
    public String createGame(Game game) {
        gameService.createGame(game);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getGameById(@PathVariable Long id, Model model) {
        var game = gameService.getGameById(id);
        var manufacturers = manufacturerService.getManufacturersByGameId(id);

        model.addAttribute("canStart", game.getGameStatus().equals(GameStatus.created)
                    || game.getGameStatus().equals(GameStatus.stageover));
        model.addAttribute("canStop", game.getGameStatus().equals(GameStatus.stagestarted));
        model.addAttribute("canOver", game.getGameStatus().equals(GameStatus.stageover));

        model.addAttribute("game", game);
        model.addAttribute("manufacturers", manufacturers);
        return "game_page";
    }

    @GetMapping("/{id}/start")
    public String startGameStage(@PathVariable Long id, Model model) {
        gameService.startGameStage(id);
        return "redirect:/" + id;
    }
    @GetMapping("/{id}/stop")
    public String stopGameStage(@PathVariable Long id, Model model) {
        gameService.stopGameStage(id);
        return "redirect:/" + id;
    }

    @RequestMapping(value = "/{id}/finish", method = {RequestMethod.PUT, RequestMethod.GET})
    public String finishGame(@PathVariable Long id) {
        gameService.finishGame(id);
        return "redirect:/";
    }

//    @GetMapping("/{id}/manufacturers")
//    public String getManufacturers(@PathVariable Long id, Model model) {
//        var manufacturers  = manufacturerService.getManufacturersByGameId(id);
//        model.addAttribute("manufacturers", manufacturers);
//        model.addAttribute("gameId", id);
//        return "manufacturers";
//    }

}
