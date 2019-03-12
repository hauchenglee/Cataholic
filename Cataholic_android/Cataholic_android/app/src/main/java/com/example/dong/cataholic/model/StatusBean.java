package com.example.dong.cataholic.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.dong.cataholic.DataConverter;
import com.example.dong.cataholic.SmallCat;

import java.io.Serializable;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = StatusBean.class, parentColumns = "memberKey", childColumns = "memberKey"),indices = @Index(value={"memberKey"},unique = true))
public class StatusBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @PrimaryKey(autoGenerate = true)
    Integer satausId;
    Long beginTime;
    Long income;
    Long saveTime;
    Boolean smoke;
    Integer pressureStatus;
    Integer generateSpeed;
    Integer money;
    Integer effectTime;
    @TypeConverters(DataConverter.class)
    List<SmallCat> smallCats;
    @TypeConverters(DataConverter.class)
    List<SmallCat> nullSmallCats;
    Long endTime;
    Integer memberKey;

    public StatusBean() {
    }

    public StatusBean(Integer satausId, Long beginTime, Long income, Long saveTime, Boolean smoke, Integer pressureStatus, Integer generateSpeed, Integer money, Integer effectTime, List<SmallCat> smallCats, List<SmallCat> nullSmallCats, Long endTime, Integer memberKey) {
        this.satausId = satausId;
        this.beginTime = beginTime;
        this.income = income;
        this.saveTime = saveTime;
        this.smoke = smoke;
        this.pressureStatus = pressureStatus;
        this.generateSpeed = generateSpeed;
        this.money = money;
        this.effectTime = effectTime;
        this.smallCats = smallCats;
        this.nullSmallCats = nullSmallCats;
        this.endTime = endTime;
        this.memberKey = memberKey;
    }

    public Integer getSatausId() {
        return satausId;
    }

    public void setSatausId(Integer satausId) {
        this.satausId = satausId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public Boolean getSmoke() {
        return smoke;
    }

    public void setSmoke(Boolean smoke) {
        this.smoke = smoke;
    }

    public Integer getPressureStatus() {
        return pressureStatus;
    }

    public void setPressureStatus(Integer pressureStatus) {
        this.pressureStatus = pressureStatus;
    }

    public Integer getGenerateSpeed() {
        return generateSpeed;
    }

    public void setGenerateSpeed(Integer generateSpeed) {
        this.generateSpeed = generateSpeed;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Integer effectTime) {
        this.effectTime = effectTime;
    }

    public List<SmallCat> getSmallCats() {
        return smallCats;
    }

    public void setSmallCats(List<SmallCat> smallCats) {
        this.smallCats = smallCats;
    }

    public List<SmallCat> getNullSmallCats() {
        return nullSmallCats;
    }

    public void setNullSmallCats(List<SmallCat> nullSmallCats) {
        this.nullSmallCats = nullSmallCats;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(Integer memberKey) {
        this.memberKey = memberKey;
    }
}
