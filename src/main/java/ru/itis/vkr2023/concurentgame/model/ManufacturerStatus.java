package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Double productCount = 0d;

    /**
     * Цена единицы продукции, выставляемая производителем
     */
    private Double price = 0d;

    /**
     * Затраты на рекламу текущего кона
     */
    private Double advertisement = 0d;

    /**
     * Ассортимент (количество типов продукции)
     */
    private Integer assortment = 1;

    /**
     * Доход (выручка)
     */
    private Double income = 0d;

    /**
     * текущие затраты
     */
    private Double expenses = 0d;

    /**
     * текущий остаток до получения выручки (используется для производства и рекламы)
     */
    private Double balance = 0d;

    public double getExpenses() {
        if (expenses != null) return expenses;
        else {
            return advertisement +
                productCount * gameStage.getGame().calculateCostPrice(productCount / advertisement);
        }
    }

    public ManufacturerStatus setId(Long id) {
        this.id = id;
        return this;
    }

    public ManufacturerStatus setBalance(Double balance) {
        this.balance = balance;
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

    public ManufacturerStatus setProductCount(Double productCount) {
        this.productCount = productCount;
        return this;
    }

    public ManufacturerStatus setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ManufacturerStatus setAdvertisement(Double advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public ManufacturerStatus setAssortment(Integer assortment) {
        this.assortment = assortment;
        return this;
    }

    public ManufacturerStatus setIncome(Double income) {
        this.income = income;
        return this;
    }
}
