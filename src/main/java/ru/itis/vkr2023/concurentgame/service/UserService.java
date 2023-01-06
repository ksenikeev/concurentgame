package ru.itis.vkr2023.concurentgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.model.security.User;
import ru.itis.vkr2023.concurentgame.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User save(User buyer) {
        return repository.save(buyer);
    }

    public User findByUserName(String name) {
        return repository.findByUserName(name);
    }
}
