package ru.itis.vkr2023.concurentgame.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Состояние (параметры) производителя в очередном этапе
 */
@Entity
@Getter@Setter
public class ManufacturerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturerStatusGen")
    @SequenceGenerator(name = "manufacturerStatusGen", sequenceName = "manufacturerstatus_seq",allocationSize = 1)
    private Long id;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private GameStage gameStage;

    /**
     * Количество продукции, которое производитель будет выпускать в этом периоде
     */
    private Float productCount;

    /**
     * Цена единицы продукции, выставляемая производителем
     */
    private Float price;

    /**
     * Затраты на рекламу текущего кона
     */
    private Float advertisement;

    /**
     * Ассортимент (количество типов продукции)
     */
    private Integer assortment;

    /**
     * Доход (выручка)
     */
    private Float income;
}
