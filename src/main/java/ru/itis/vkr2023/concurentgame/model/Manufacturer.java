package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.vkr2023.concurentgame.model.sequrity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Производитель
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
