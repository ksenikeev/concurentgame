package ru.itis.vkr2023.concurentgame.model;

import lombok.Getter;
import lombok.Setter;
import ru.itis.vkr2023.concurentgame.model.sequrity.User;
import javax.persistence.*;

/**
 * Производитель
 */
@Entity
@Getter@Setter
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturerGen")
    @SequenceGenerator(name = "manufacturerGen", sequenceName = "manufacturer_seq",allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne
    private Game game;

    @ManyToOne
    private User user;
}
