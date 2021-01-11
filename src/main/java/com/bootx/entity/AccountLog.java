package com.bootx.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 充值记录
 * @author black
 */
@Entity
public class AccountLog extends BaseEntity<Long>{

    public Long userId;

    private String mobile;

    private String name;

    private String title;

    /**
     * 审核状态：0：审核中，1：已到账 其他：未到账
     */
    private Integer state;

    @Column(precision = 27, scale = 12)
    private BigDecimal money;

    @Column(precision = 27, scale = 12)
    private BigDecimal beforeMoney;

    @Column(precision = 27, scale = 12)
    private BigDecimal afterMoney;

    private Integer assetType;

    /**
     * 类型：
     * 1：增加余额
     * 其他：减少余额
     */
    private Integer dataType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }
}
