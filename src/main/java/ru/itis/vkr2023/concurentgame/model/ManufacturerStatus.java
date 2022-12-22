package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Состояние (параметры) производителя в очередном этапе
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public ManufacturerStatus setId(Long id) {
        this.id = id;
        return this;
    }

    public ManufacturerStatus setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public ManufacturerStatus setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
        return this;
    }

    public ManufacturerStatus setProductCount(Float productCount) {
        this.productCount = productCount;
        return this;
    }

    public ManufacturerStatus setPrice(Float price) {
        this.price = price;
        return this;
    }

    public ManufacturerStatus setAdvertisement(Float advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public ManufacturerStatus setAssortment(Integer assortment) {
        this.assortment = assortment;
        return this;
    }

    public ManufacturerStatus setIncome(Float income) {
        this.income = income;
        return this;
    }
}
