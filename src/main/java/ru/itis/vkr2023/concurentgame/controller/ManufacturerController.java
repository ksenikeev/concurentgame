package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.vkr2023.concurentgame.model.*;
import ru.itis.vkr2023.concurentgame.security.UserDetailsImpl;
import ru.itis.vkr2023.concurentgame.service.GameService;
import ru.itis.vkr2023.concurentgame.service.ManufacturerService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    private final GameService gameService;

    @GetMapping("/manufacturer/{manufacturerId}")
    public String getEnterParametersPage( @PathVariable Long manufacturerId, Model model) {

        Optional<Game> game = gameService.getCurrentGame();
        if (game.isPresent() && game.get().getGameStatus().equals(GameStatus.stagestarted)) {
            model.addAttribute("showParam", true);
        } else {
            model.addAttribute("showParam", false);
        }
        //model.addAttribute("gameId", gameId);
        model.addAttribute("statusId", manufacturerId);
        return "enter_parameters";
    }

    @PostMapping("/join")
    public String joinGame(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String name, Model model) {
        Optional<Game> currentGame = gameService.getCurrentGame();
        if (currentGame.isPresent()) {
            var manufacturer = manufacturerService.joinGame(userDetails, currentGame.get(), name);
            //return "redirect:/" + currentGame.get().getId() + "/manufacturer/" + manufacturerStatus.getId() + "/parameters";
            return "redirect:/manufacturer/" + manufacturer.getId();
        } else {
            return "active_game_not_found";
        }
    }

    @PostMapping("/manufacturer-status/{manufacturerId}")
    public String enterParameters( @PathVariable Long manufacturerId, ManufacturerStatus manufacturerStatus, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
        if (manufacturer != null) {
            Game game = manufacturer.getGame();
            if (!game.getGameStatus().equals(GameStatus.stagestarted)) {
                return "error?stage_not_starter";
            }
            List<GameStage> stages = gameService.getGameStageByGame(manufacturer.getGame());
            if (!stages.isEmpty()) {
                GameStage gameStage = stages.get(0);

                manufacturerService.enterParameters(manufacturer, gameStage, manufacturerStatus);

                return "redirect:/manufacturer/" + manufacturerId;
            } else {
                return "game_stage_not_started";
            }
        } else {
            return "error?manufacturer_not_found";
        }
    }
}
