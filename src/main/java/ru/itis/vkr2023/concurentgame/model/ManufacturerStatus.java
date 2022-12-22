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
    private Double productCount;

    /**
     * Цена единицы продукции, выставляемая производителем
     */
    private Double price;

    /**
     * Затраты на рекламу текущего кона
     */
    private Double advertisement;

    /**
     * Ассортимент (количество типов продукции)
     */
    private Integer assortment;

    /**
     * Доход (выручка)
     */
    private Double income;

    /**
     * текущие затраты
     */
    @Transient
    private transient Double expenses;

    /**
     * текущий остаток до получения выручки (используется для производства и рекламы)
     */
    @Transient
    private transient Double balanse;

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
