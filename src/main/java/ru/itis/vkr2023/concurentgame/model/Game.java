package ru.itis.vkr2023.concurentgame.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
    Параметры игры
 */
@Entity
@Getter@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameGen")
    @SequenceGenerator(name = "gameGen", sequenceName = "game_seq",allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /**
     * Стартовый капитал N каждого производителя
     */
    private Double startUpCapital;

    /**
     * Базовая себестоимость единицы продукции
     */
    private Double costPrice;

    /**
     * Бюджет каждого потребителя (на каждый заданный период)
     */
    private Double buyersBudget;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;


}
