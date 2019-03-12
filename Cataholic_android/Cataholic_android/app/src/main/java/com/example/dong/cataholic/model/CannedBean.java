package com.example.dong.cataholic.model;

import java.io.Serializable;

public class CannedBean implements Serializable {
    String cannedName;
    Integer cannedPrice;

    Integer cannedDuration;
    Integer cannedSpeed;
    Integer cannedUnitMoney;
    Integer amount;

    public CannedBean(String cannedName, Integer cannedPrice, Integer cannedDuration, Integer cannedSpeed, Integer cannedUnitMoney, Integer amount) {
        this.cannedName = cannedName;
        this.cannedPrice = cannedPrice;
        this.cannedDuration = cannedDuration;
        this.cannedSpeed = cannedSpeed;
        this.cannedUnitMoney = cannedUnitMoney;
        this.amount = amount;
    }

    public String getCannedName() {
        return cannedName;
    }

    public void setCannedName(String cannedName) {
        this.cannedName = cannedName;
    }

    public Integer getCannedPrice() {
        return cannedPrice;
    }

    public void setCannedPrice(Integer cannedPrice) {
        this.cannedPrice = cannedPrice;
    }

    public Integer getCannedDuration() {
        return cannedDuration;
    }

    public void setCannedDuration(Integer cannedDuration) {
        this.cannedDuration = cannedDuration;
    }

    public Integer getCannedSpeed() {
        return cannedSpeed;
    }

    public void setCannedSpeed(Integer cannedSpeed) {
        this.cannedSpeed = cannedSpeed;
    }

    public Integer getCannedUnitMoney() {
        return cannedUnitMoney;
    }

    public void setCannedUnitMoney(Integer cannedUnitMoney) {
        this.cannedUnitMoney = cannedUnitMoney;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
