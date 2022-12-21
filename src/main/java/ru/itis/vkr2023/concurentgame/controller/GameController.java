package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.service.GameService;

@Controller
@RequiredArgsConstructor
public class GameController {

    private  final GameService gameService;

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
        model.addAttribute("game", game);
        return "game_page";
    }

    @PutMapping("/{id}/start")
    public String startGame(@PathVariable Long id) {
        gameService.startGameStage(id);
        return "manufacturers";
    }

    @RequestMapping(value = "/{id}/finish", method = {RequestMethod.PUT, RequestMethod.GET})
    public String finishGame(@PathVariable Long id) {
        gameService.finishGame(id);
        return "redirect:/";
    }

}
