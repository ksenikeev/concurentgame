package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.GameStatus;
import ru.itis.vkr2023.concurentgame.model.Manufacturer;
import ru.itis.vkr2023.concurentgame.model.security.UserRole;
import ru.itis.vkr2023.concurentgame.security.UserDetailsImpl;
import ru.itis.vkr2023.concurentgame.service.GameService;
import ru.itis.vkr2023.concurentgame.service.ManufacturerService;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final GameService gameService;
    private final ManufacturerService manufacturerService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        Optional<Game> currentGame = gameService.getCurrentGame();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails.getUser().getRole().equals(UserRole.ADMINISTRATOR)) {
            // Ветка Администратора
            model.addAttribute("games", gameService.getAllGames());

            return "index_admin";
        } else if (userDetails.getUser().getRole().equals(UserRole.MANIFACTURER)) {
            // Ветка производителя
            boolean canJoin = false;
            boolean inActiveGame = false;

            // Если есть созданная игра, то можно присоединиться
            if (currentGame.isPresent() &&
                    currentGame.get().getGameStatus().equals(GameStatus.created)) {
                canJoin = true;
            } else {
                // Если пользователь является участником активной игры подаем ее
                Game game = gameService.findActiveUserGame(userDetails.getUser());

                if (game != null) {
                    Manufacturer m = manufacturerService.findByGameIdAndUserId(game.getId(), userDetails.getUser().getId());
                    inActiveGame = true;
                    model.addAttribute("manufacturerId", String.valueOf(m.getId()));
                    model.addAttribute("game", game);
                }
            }

            model.addAttribute("inActiveGame", inActiveGame);
            model.addAttribute("canJoin", canJoin);

            return "index_manufacturer";
        } else
            return "";
    }
}
