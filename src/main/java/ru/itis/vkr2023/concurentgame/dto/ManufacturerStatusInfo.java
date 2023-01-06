package ru.itis.vkr2023.concurentgame.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManufacturerStatusInfo {

    private String startDate;

    private Double productCount;

    private Double price;

    private Double advertisement;

    private Integer assortment;

    private Double income;

    private Double balance;

    private String name;

    private Double expenses = 0d;

    public ManufacturerStatusInfo(Date startDate, Double productCount, Double price, Double advertisement,
                                  Integer assortment, Double income, Double balance, String name) {
        this.startDate = startDate != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(startDate) : "";
        this.productCount = productCount;
        this.price = price;
        this.advertisement = advertisement;
        this.assortment = assortment;
        this.income = income;
        this.balance = balance;
        this.name = name;
    }

    public ManufacturerStatusInfo(Date startDate, Double productCount, Double price, Double advertisement,
                                  Integer assortment, Double income, Double balance) {
        this.startDate = startDate != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(startDate) : "";
        this.productCount = productCount;
        this.price = price;
        this.advertisement = advertisement;
        this.assortment = assortment;
        this.income = income;
        this.balance = balance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(startDate) : "";
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Double advertisement) {
        this.advertisement = advertisement;
    }

    public Integer getAssortment() {
        return assortment;
    }

    public void setAssortment(Integer assortment) {
        this.assortment = assortment;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
