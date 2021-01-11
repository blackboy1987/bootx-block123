package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


/**
 * {
 *                 "id":1763114,
 *                 "userId":218776,
 *                 "assetType":5,
 *                 "money":0,
 *                 "frozenMoney":0,
 *                 "state":true,
 *                 "name":"ETH",
 *                 "price":"0.00"
 *             }
 * @author black
 */
@Entity
@Table(name = "BitCoinAccount", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "name" })})
public class BitCoinAccount extends BaseEntity<Long> {

    private Long userId;

    @JsonView({PageView.class})
    private Integer assetType;

    @Column(precision = 27, scale = 12)
    @JsonView({PageView.class})
    private BigDecimal money;

    @Column(precision = 27, scale = 12)
    @JsonView({PageView.class})
    private BigDecimal frozenMoney;

    private Boolean state;

    @JsonView({PageView.class})
    private String name;

    @Column(precision = 27, scale = 12)
    private BigDecimal price;

    private String addressId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
