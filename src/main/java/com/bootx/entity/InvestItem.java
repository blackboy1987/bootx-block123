package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 收益
 */
@Entity
public class InvestItem extends BaseEntity<Long>{

    private Long investId;

    private Long userId;

    private Long productId;

    @Column(precision = 27, scale = 12)
    private BigDecimal electric;

    @Column(precision = 27, scale = 12)
    private BigDecimal manage;

    @Column(precision = 27, scale = 12)
    private BigDecimal profit;

    @Column(precision = 27, scale = 12)
    private BigDecimal btcPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal hptPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal invest;

    @Column(precision = 27, scale = 12)
    private BigDecimal investAmount;

    @Column(precision = 27, scale = 12)
    private BigDecimal investBtc;

    @Column(precision = 27, scale = 12)
    private BigDecimal investHpt;

    @Column(precision = 27, scale = 12)
    private BigDecimal investEth;

    private Integer state;

    private String userName;

    private String name;

    private String phone;

    public Long getInvestId() {
        return investId;
    }

    public void setInvestId(Long investId) {
        this.investId = investId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getElectric() {
        return electric;
    }

    public void setElectric(BigDecimal electric) {
        this.electric = electric;
    }

    public BigDecimal getManage() {
        return manage;
    }

    public void setManage(BigDecimal manage) {
        this.manage = manage;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(BigDecimal btcPrice) {
        this.btcPrice = btcPrice;
    }

    public BigDecimal getHptPrice() {
        return hptPrice;
    }

    public void setHptPrice(BigDecimal hptPrice) {
        this.hptPrice = hptPrice;
    }

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public BigDecimal getInvestBtc() {
        return investBtc;
    }

    public void setInvestBtc(BigDecimal investBtc) {
        this.investBtc = investBtc;
    }

    public BigDecimal getInvestHpt() {
        return investHpt;
    }

    public void setInvestHpt(BigDecimal investHpt) {
        this.investHpt = investHpt;
    }

    public BigDecimal getInvestEth() {
        return investEth;
    }

    public void setInvestEth(BigDecimal investEth) {
        this.investEth = investEth;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
