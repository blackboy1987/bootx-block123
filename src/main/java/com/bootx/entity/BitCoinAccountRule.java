package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author black
 */
@Entity
@Table(name = "BitCoinAccountRule")
public class BitCoinAccountRule extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private Integer assetType;

    private Boolean canRecharge;

    private Boolean canWithdraw;

    private Boolean canTrade;

    @Column(precision = 27, scale = 12)
    private BigDecimal rechargeLimit;

    @Column(precision = 27, scale = 12)
    private BigDecimal rechargeRate;

    @Column(precision = 27, scale = 12)
    private BigDecimal rechargeMax;

    @Column(precision = 27, scale = 12)
    private BigDecimal withdrawLimit;

    @Column(precision = 27, scale = 12)
    private BigDecimal withdrawRate;

    @Column(precision = 27, scale = 12)
    private BigDecimal withdrawMax;

    private Boolean activation;

    private Long withdrawProduct;

    private Boolean withdrawAuth;

    private String productName;

    private String name;

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Boolean getCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(Boolean canRecharge) {
        this.canRecharge = canRecharge;
    }

    public Boolean getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(Boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public Boolean getCanTrade() {
        return canTrade;
    }

    public void setCanTrade(Boolean canTrade) {
        this.canTrade = canTrade;
    }

    public BigDecimal getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(BigDecimal rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }

    public BigDecimal getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(BigDecimal rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public BigDecimal getRechargeMax() {
        return rechargeMax;
    }

    public void setRechargeMax(BigDecimal rechargeMax) {
        this.rechargeMax = rechargeMax;
    }

    public BigDecimal getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getWithdrawRate() {
        return withdrawRate;
    }

    public void setWithdrawRate(BigDecimal withdrawRate) {
        this.withdrawRate = withdrawRate;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public Long getWithdrawProduct() {
        return withdrawProduct;
    }

    public void setWithdrawProduct(Long withdrawProduct) {
        this.withdrawProduct = withdrawProduct;
    }

    public Boolean getWithdrawAuth() {
        return withdrawAuth;
    }

    public void setWithdrawAuth(Boolean withdrawAuth) {
        this.withdrawAuth = withdrawAuth;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
