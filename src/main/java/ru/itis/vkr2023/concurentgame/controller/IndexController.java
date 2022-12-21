package ru.itis.vkr2023.concurentgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.vkr2023.concurentgame.model.sequrity.UserRole;
import ru.itis.vkr2023.concurentgame.security.UserDetailImpl;
import ru.itis.vkr2023.concurentgame.service.GameService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final GameService gameService;

    @GetMapping("/")
    public String loginForm(HttpServletRequest request, Model model) {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUser().getRole().equals(UserRole.ADMINISTRATOR)) {
            model.addAttribute("games", gameService.getAllGames());
            return "index_admin";
        }
        else if (userDetails.getUser().getRole().equals(UserRole.MANIFACTURER))
            return "index_manufacturer";
        else
            return "";
    }
}
