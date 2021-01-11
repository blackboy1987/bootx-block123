package com.bootx.entity;

import javax.persistence.Entity;

@Entity
public class ReceiptAccount extends BaseEntity<Long>{

    private Long userId;

    private String bankCard;

    private String theirBank;

    private String area;

    private String name;

    private Integer type;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getTheirBank() {
        return theirBank;
    }

    public void setTheirBank(String theirBank) {
        this.theirBank = theirBank;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
