package ru.itis.vkr2023.concurentgame.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.model.sequrity.User;
import ru.itis.vkr2023.concurentgame.repository.UserRepository;

@Service(value = "myUserDetailsServise")
public class UserDetailsServiseImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        if (user != null) {
            return new UserDetailsImpl(user);
        } throw new UsernameNotFoundException("User not found!");
    }
}
