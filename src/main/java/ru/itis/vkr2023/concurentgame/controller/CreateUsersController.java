package ru.itis.vkr2023.concurentgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.vkr2023.concurentgame.model.Buyer;
import ru.itis.vkr2023.concurentgame.model.sequrity.User;
import ru.itis.vkr2023.concurentgame.model.sequrity.UserRole;
import ru.itis.vkr2023.concurentgame.service.BuyerService;
import ru.itis.vkr2023.concurentgame.service.UserService;
import ru.itis.vkr2023.concurentgame.service.buyer.BuyerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Формирование списка пользователей и покупателей из csv файла
 */
@Controller
public class CreateUsersController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private UserService userService;

    @GetMapping("/cusers")
    //@Secured({"ADMINISTRATOR"})
    public String loginForm(HttpServletRequest request, Model model) {

        List<Buyer> buyers = BuyerFactory.loadFromCSV();

        buyers.forEach(buyer -> {
            buyerService.save(buyer);

            User user = new User();
            user.setName(buyer.getName() + ", " + buyer.getGroupName());
            user.setUserName(Long.toString(buyer.getId()));
            user.setPassword(BCrypt.hashpw(Long.toString(buyer.getId()), BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        });

        return "/";
    }
}
