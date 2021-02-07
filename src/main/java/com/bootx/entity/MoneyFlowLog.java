package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class MoneyFlowLog extends BaseEntity<Long>{

    @NotNull
    @Column(nullable = false,updatable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Integer coinType;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String coinName;

    @NotNull
    @Column(precision = 27, scale = 12,nullable = false,updatable = false)
    private BigDecimal money;

    @NotNull
    @Column(precision = 27, scale = 12,nullable = false,updatable = false)
    private BigDecimal beforeMoney;
    @NotNull
    @Column(precision = 27, scale = 12,nullable = false,updatable = false)
    private BigDecimal frozenMoney;

    @NotNull
    @Column(precision = 27, scale = 12,nullable = false,updatable = false)
    private BigDecimal afterMoney;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String memo;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(BigDecimal beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
