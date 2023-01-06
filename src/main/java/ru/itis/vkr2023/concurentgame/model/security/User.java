package ru.itis.vkr2023.concurentgame.model.security;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * Пользователь системы
 */
@Entity
@Table(name = "users")
@Getter@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @SequenceGenerator(name = "userGen", sequenceName = "users_seq",allocationSize = 1)
    private Long id;

    private String name;

    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
