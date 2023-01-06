package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.vkr2023.concurentgame.dto.ManufacturerStatusInfo;
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
            model.addAttribute("cost", String.valueOf(game.get().getCostPrice()));
            model.addAttribute("cost95", String.valueOf(game.get().getCostPrice() * 0.95));
            model.addAttribute("cost90", String.valueOf(game.get().getCostPrice() * 0.9));
            model.addAttribute("cost85", String.valueOf(game.get().getCostPrice() * 0.85));
            model.addAttribute("cost80", String.valueOf(game.get().getCostPrice() * 0.8));
            model.addAttribute("cost75", String.valueOf(game.get().getCostPrice() * 0.75));

            ManufacturerStatus status = manufacturerService.getLastManufacturerStatusByManufacturerId(manufacturerId);
            if (status != null) {
                model.addAttribute("balance", String.valueOf(status.getBalance().longValue()));
                model.addAttribute("productCount", String.valueOf(status.getProductCount()));
                model.addAttribute("price", String.valueOf(status.getPrice()));
                model.addAttribute("advertisement", String.valueOf(status.getAdvertisement()));
                model.addAttribute("assortment", String.valueOf(status.getAssortment()));
            }
        } else {
            model.addAttribute("showParam", false);
        }
        //model.addAttribute("gameId", gameId);
        model.addAttribute("statusId", String.valueOf(manufacturerId));
        return "enter_parameters";
    }

    @PostMapping("/join")
    public String joinGame(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String name, Model model) {
        Optional<Game> currentGame = gameService.getCurrentGame();
        if (currentGame.isPresent()) {
            Game game = gameService.findActiveUserGame(userDetails.getUser());
            Manufacturer manufacturer = null;
            if (game == null) {
                manufacturer = manufacturerService.joinGame(userDetails, currentGame.get(), name);
            } else {
                manufacturer = manufacturerService.findByGameIdAndUserId(currentGame.get().getId(), userDetails.getUser().getId());
            }
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
            if (!stages.isEmpty() && stages.get(0).getEndDate() == null) {
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

    /**
     * Поэтапная статистика производителя
     */
    @GetMapping("/manufacturer-status/info/{manufacturerId}")
    public String getManufacturerStatusInfo( @PathVariable Long manufacturerId, Model model) {

        List<ManufacturerStatusInfo> lst = manufacturerService.findAllInfoByManufacturerIdOrderById(manufacturerId);

        // Пересчитаем поэтапно производственные расходы
        Game game = manufacturerService.findGameByManufacturerId(manufacturerId);

        lst.forEach(s -> {
            s.setExpenses(
                    game.calculateCostPrice(s.getProductCount() / s.getAssortment())
                        * s.getProductCount());
        });

        model.addAttribute("statusId", String.valueOf(manufacturerId));
        model.addAttribute("statusInfoLst", lst);

        return "manufacturer_info";
    }

    @GetMapping("/manufacturer-status/info/admin/{gameId}")
    public String getManufacturerStatusInfoAdmin( @PathVariable Long gameId, Model model) {

        List<ManufacturerStatusInfo> lst = manufacturerService.findAllInfoByGameIdOrderById(gameId);
        model.addAttribute("gameId", String.valueOf(gameId));
        model.addAttribute("statusInfoLst", lst);

        return "manufacturer_info_admin";
    }
}
