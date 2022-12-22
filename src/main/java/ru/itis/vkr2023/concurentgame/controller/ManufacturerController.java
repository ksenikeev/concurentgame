package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStage;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;
import ru.itis.vkr2023.concurentgame.security.UserDetailsImpl;
import ru.itis.vkr2023.concurentgame.service.GameService;
import ru.itis.vkr2023.concurentgame.service.ManufacturerService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    private final GameService gameService;

    @GetMapping("/{gameId}/manufacturer-status/{statusId}")
    public String getEnterParametersPage(@PathVariable Long gameId, @PathVariable Long statusId, Model model) {
        model.addAttribute("gameId", gameId);
        model.addAttribute("statusId", statusId);
        return "enter_parameters";
    }

    @PostMapping("/join")
    public String joinGame(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String name, Model model) {
        Optional<Game> currentGame = gameService.getCurrentGame();
        if (currentGame.isPresent()) {
            var manufacturerStatus = manufacturerService.joinGame(userDetails, currentGame.get(), name);
            return "redirect:/" + currentGame.get().getId() + "/manufacturer/" + manufacturerStatus.getId() + "/parameters";
        } else {
            return "active_game_not_found";
        }
    }

    @PostMapping("/{gameId}/manufacturer-status/{statusId}")
    public String enterParameters(@PathVariable Long gameId,
                                  @PathVariable Long statusId,
                                  ManufacturerStatus manufacturerStatus,
                                  Model model) {
        Optional<GameStage> stage = gameService.getGameStageByGameId(gameId);
        if (stage.isPresent()) {
            manufacturerService.enterParameters(statusId, manufacturerStatus);
            return "redirect:/" + gameId + "/manufacturer-status/" + statusId;
        } else {
            return "game_stage_not_started";
        }
    }
}
