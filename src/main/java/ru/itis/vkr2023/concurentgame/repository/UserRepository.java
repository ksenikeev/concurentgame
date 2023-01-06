package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itis.vkr2023.concurentgame.model.security.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.userName = :name ")
    User findByUserName(@Param("name") String name);
}
