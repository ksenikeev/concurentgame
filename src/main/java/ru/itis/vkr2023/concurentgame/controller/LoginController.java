package ru.itis.vkr2023.concurentgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.vkr2023.concurentgame.repository.UserRepository;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "loginform";
    }

    @PostMapping("/usercheck")
    public String loginForm(HttpServletRequest request, Model model) {

        try {
            request.login(request.getParameter("username"), request.getParameter("password"));
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "/";
    }
}
