package ru.itis.vkr2023.concurentgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.vkr2023.concurentgame.model.Buyer;
import ru.itis.vkr2023.concurentgame.model.security.User;
import ru.itis.vkr2023.concurentgame.model.security.UserRole;
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

/*
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
*/
        {
            User user = new User();
            user.setName("Камиль");
            user.setUserName("Камиль");
            user.setPassword(BCrypt.hashpw("Камиль", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Зульфира");
            user.setUserName("Зульфира");
            user.setPassword(BCrypt.hashpw("Зульфира", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Саида");
            user.setUserName("Саида");
            user.setPassword(BCrypt.hashpw("Саида", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Самира");
            user.setUserName("Самира");
            user.setPassword(BCrypt.hashpw("Самира", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Мурад");
            user.setUserName("Мурад");
            user.setPassword(BCrypt.hashpw("Мурад", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Сюмбель");
            user.setUserName("Сюмбель");
            user.setPassword(BCrypt.hashpw("Сюмбель", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Алмаз");
            user.setUserName("Алмаз");
            user.setPassword(BCrypt.hashpw("Алмаз", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Илдар");
            user.setUserName("Илдар");
            user.setPassword(BCrypt.hashpw("Илдар", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Эльмира");
            user.setUserName("Эльмира");
            user.setPassword(BCrypt.hashpw("Эльмира", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }
        {
            User user = new User();
            user.setName("Динар");
            user.setUserName("Динар");
            user.setPassword(BCrypt.hashpw("Динар", BCrypt.gensalt(12)));
            user.setRole(UserRole.MANIFACTURER);
            userService.save(user);
        }

        return "/";
    }
}
