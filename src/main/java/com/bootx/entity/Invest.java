package com.bootx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收益
 */
@Entity
public class Invest extends BaseEntity<Long>{

    private Long userId;

    private Long productId;

    private String productName;

    @Column(precision = 27, scale = 12)
    private BigDecimal invest;

    @Column(precision = 27, scale = 12)
    private BigDecimal frozenInvest;

    @Column(precision = 27, scale = 12)
    private BigDecimal frozenInvestTemp;

    @Column(precision = 27, scale = 12)
    private BigDecimal allBtc;

    @Column(precision = 27, scale = 12)
    private BigDecimal allHpt;

    @Column(precision = 27, scale = 12)
    private BigDecimal allEth;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastEth;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastBtc;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastHpt;

    /**
     * 到期时间
     */
    private Date lastTime;

    private Date frozenTime;

    private Date investTime;

    @Column(precision = 27, scale = 12)
    private BigDecimal returnMoney;

    private Integer returnDays;

    private String userName;

    private String phone;

    private Boolean isExpire;

    private Long validity;

    private Long orderId;

    private String memo;

    @Column(precision = 27, scale = 12)
    private BigDecimal allBtcPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal allHptPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastBtcPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastHptPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal allEthPrice;

    @Column(precision = 27, scale = 12)
    private BigDecimal lastEthPrice;

    private Integer type;

    @Column(precision = 27, scale = 12)
    private BigDecimal profit;

    private Long profitYear;

    @Column(precision = 27, scale = 12)
    private BigDecimal electric;

    @Column(precision = 27, scale = 12)
    private BigDecimal electricDiscount;

    @Column(precision = 27, scale = 12)
    private BigDecimal manage;

    @Column(precision = 27, scale = 12)
    private BigDecimal manageDiscount;

    @Column(precision = 27, scale = 12)
    private BigDecimal btcDiscount;

    @Column(precision = 27, scale = 12)
    private BigDecimal hbtDiscount;

    private Date expireDate;

    private Date comeDate;

    private Date expirationDate;

    private Integer coinType;

    private Integer excision;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getFrozenInvest() {
        return frozenInvest;
    }

    public void setFrozenInvest(BigDecimal frozenInvest) {
        this.frozenInvest = frozenInvest;
    }

    public BigDecimal getFrozenInvestTemp() {
        return frozenInvestTemp;
    }

    public void setFrozenInvestTemp(BigDecimal frozenInvestTemp) {
        this.frozenInvestTemp = frozenInvestTemp;
    }

    public BigDecimal getAllBtc() {
        return allBtc;
    }

    public void setAllBtc(BigDecimal allBtc) {
        this.allBtc = allBtc;
    }

    public BigDecimal getAllHpt() {
        return allHpt;
    }

    public void setAllHpt(BigDecimal allHpt) {
        this.allHpt = allHpt;
    }

    public BigDecimal getAllEth() {
        return allEth;
    }

    public void setAllEth(BigDecimal allEth) {
        this.allEth = allEth;
    }

    public BigDecimal getLastEth() {
        return lastEth;
    }

    public void setLastEth(BigDecimal lastEth) {
        this.lastEth = lastEth;
    }

    public BigDecimal getLastBtc() {
        return lastBtc;
    }

    public void setLastBtc(BigDecimal lastBtc) {
        this.lastBtc = lastBtc;
    }

    public BigDecimal getLastHpt() {
        return lastHpt;
    }

    public void setLastHpt(BigDecimal lastHpt) {
        this.lastHpt = lastHpt;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getFrozenTime() {
        return frozenTime;
    }

    public void setFrozenTime(Date frozenTime) {
        this.frozenTime = frozenTime;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(Integer returnDays) {
        this.returnDays = returnDays;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Boolean isExpire) {
        this.isExpire = isExpire;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public BigDecimal getAllBtcPrice() {
        return allBtcPrice;
    }

    public void setAllBtcPrice(BigDecimal allBtcPrice) {
        this.allBtcPrice = allBtcPrice;
    }

    public BigDecimal getAllHptPrice() {
        return allHptPrice;
    }

    public void setAllHptPrice(BigDecimal allHptPrice) {
        this.allHptPrice = allHptPrice;
    }

    public BigDecimal getLastBtcPrice() {
        return lastBtcPrice;
    }

    public void setLastBtcPrice(BigDecimal lastBtcPrice) {
        this.lastBtcPrice = lastBtcPrice;
    }

    public BigDecimal getLastHptPrice() {
        return lastHptPrice;
    }

    public void setLastHptPrice(BigDecimal lastHptPrice) {
        this.lastHptPrice = lastHptPrice;
    }

    public BigDecimal getAllEthPrice() {
        return allEthPrice;
    }

    public void setAllEthPrice(BigDecimal allEthPrice) {
        this.allEthPrice = allEthPrice;
    }

    public BigDecimal getLastEthPrice() {
        return lastEthPrice;
    }

    public void setLastEthPrice(BigDecimal lastEthPrice) {
        this.lastEthPrice = lastEthPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getProfitYear() {
        return profitYear;
    }

    public void setProfitYear(Long profitYear) {
        this.profitYear = profitYear;
    }

    public BigDecimal getElectric() {
        return electric;
    }

    public void setElectric(BigDecimal electric) {
        this.electric = electric;
    }

    public BigDecimal getElectricDiscount() {
        return electricDiscount;
    }

    public void setElectricDiscount(BigDecimal electricDiscount) {
        this.electricDiscount = electricDiscount;
    }

    public BigDecimal getManage() {
        return manage;
    }

    public void setManage(BigDecimal manage) {
        this.manage = manage;
    }

    public BigDecimal getManageDiscount() {
        return manageDiscount;
    }

    public void setManageDiscount(BigDecimal manageDiscount) {
        this.manageDiscount = manageDiscount;
    }

    public BigDecimal getBtcDiscount() {
        return btcDiscount;
    }

    public void setBtcDiscount(BigDecimal btcDiscount) {
        this.btcDiscount = btcDiscount;
    }

    public BigDecimal getHbtDiscount() {
        return hbtDiscount;
    }

    public void setHbtDiscount(BigDecimal hbtDiscount) {
        this.hbtDiscount = hbtDiscount;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getComeDate() {
        return comeDate;
    }

    public void setComeDate(Date comeDate) {
        this.comeDate = comeDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public Integer getExcision() {
        return excision;
    }

    public void setExcision(Integer excision) {
        this.excision = excision;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
