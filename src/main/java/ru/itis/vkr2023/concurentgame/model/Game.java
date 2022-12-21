package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
    Параметры игры
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public Long getId() {
        return id;
    }

    public Game setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Game setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Game setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Double getStartUpCapital() {
        return startUpCapital;
    }

    public Game setStartUpCapital(Double startUpCapital) {
        this.startUpCapital = startUpCapital;
        return this;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public Game setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public Double getBuyersBudget() {
        return buyersBudget;
    }

    public Game setBuyersBudget(Double buyersBudget) {
        this.buyersBudget = buyersBudget;
        return this;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Game setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        return this;
    }
}
