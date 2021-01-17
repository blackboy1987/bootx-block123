package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


@Entity
@Table(name = "BitCoinAccountMoney", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "bitCoinAccountId","assetType" })})
public class BitCoinAccountMoney extends BaseEntity<Long> {

    private Long userId;

    private Long bitCoinAccountId;

    private Integer assetType;

    @Column(precision = 27, scale = 12)
    private BigDecimal money;

    @Column(precision = 27, scale = 12)
    private BigDecimal frozenMoney;

    @Column(precision = 27, scale = 12)
    private BigDecimal lockMoney;

    private Integer state;

    private String name;

    @Column(precision = 27, scale = 12)
    private BigDecimal price;

    public BitCoinAccountMoney() {
    }

    public BitCoinAccountMoney(Long userId, BitCoinAccount bitCoinAccount) {
        this.userId = userId;
        this.bitCoinAccountId = bitCoinAccount.getId();
        this.assetType = bitCoinAccount.getAssetType();
        this.money = BigDecimal.ZERO;
        this.frozenMoney = BigDecimal.ZERO;
        this.state = 0;
        this.name = bitCoinAccount.getName();
        this.price = bitCoinAccount.getPrice();
        this.lockMoney = BigDecimal.ZERO;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBitCoinAccountId() {
        return bitCoinAccountId;
    }

    public void setBitCoinAccountId(Long bitCoinAccountId) {
        this.bitCoinAccountId = bitCoinAccountId;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public BigDecimal getLockMoney() {
        return lockMoney;
    }

    public void setLockMoney(BigDecimal lockMoney) {
        this.lockMoney = lockMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
