package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author black
 */
@Entity
public class MineMachineOrder extends BaseEntity<Long>{

    private Long creator;
    private Long modifier;
    @JsonView({PageView.class})
    private Long userId;
    @JsonView({PageView.class})
    private Integer productType;
    private Long productId;
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal price;
    private Integer invest;
    @JsonView({PageView.class})
    private Integer quantity;
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal amount;
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal discount;
    @JsonView({PageView.class})
    private String memo;
    /**
     * 订单状态
     * 0：已创建
     * 1：去支付
     * 2：已支付
     * 3：已完成
     * 4：已取消
     * 5：退款中
     * 6：已退款
     * 7: 已过期
     * 其他：已取消
     */
    @JsonView({PageView.class})
    private Integer state;
    private Integer payType;
    private String payPrice;
    private String terminationDate;
    private Integer twelveSmstag;
    private Integer oneSmstag;
    private Integer earnest;
    private Integer balancePayment;
    private String returnMoney;
    @JsonView({PageView.class})
    private Integer electricType;
    @JsonView({PageView.class})
    private Integer day;
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal addElectric;
    @Column(precision = 27, scale = 12)
    private BigDecimal electricMoney;
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal rmbPrice;
    @JsonView({PageView.class})
    private Integer coinType;
    @JsonView({PageView.class})
    private String fromChannel;
    private String electric;
    private Date expirationDate;
    private String comeDate;
    private String expireDate;
    private String investTime;
    @JsonView({PageView.class})
    private String sn;
    @JsonView({PageView.class})
    private String userName;
    @JsonView({PageView.class})
    private String phone;
    @JsonView({PageView.class})
    private String name;
    @JsonView({PageView.class})
    private String productName;
    @JsonView({PageView.class})
    private String productIcon;
    @JsonView({PageView.class})
    private String productManage;
    private String manage;
    private String productManageDiscount;
    private String productElectric;
    private String rmbElectricPrice;
    private String saleType;
    private String productValidity;
    private String profitUsdt;
    private String child;
    private Date today;
    private String shopCode;
    private String electricDiscount;
    private String profit;
    private String isReward;
    private String btcDiscount;
    private String hbtDiscount;
    private String electricDicountMd5Format;
    private String encapsulationDay;
    private String reward;
    private Integer orderType;
    private Integer excision;

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getInvest() {
        return invest;
    }

    public void setInvest(Integer invest) {
        this.invest = invest;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public int getTwelveSmstag() {
        return twelveSmstag;
    }

    public void setTwelveSmstag(int twelveSmstag) {
        this.twelveSmstag = twelveSmstag;
    }

    public int getOneSmstag() {
        return oneSmstag;
    }

    public void setOneSmstag(int oneSmstag) {
        this.oneSmstag = oneSmstag;
    }

    public int getEarnest() {
        return earnest;
    }

    public void setEarnest(int earnest) {
        this.earnest = earnest;
    }

    public int getBalancePayment() {
        return balancePayment;
    }

    public void setBalancePayment(int balancePayment) {
        this.balancePayment = balancePayment;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public int getElectricType() {
        return electricType;
    }

    public void setElectricType(int electricType) {
        this.electricType = electricType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public BigDecimal getAddElectric() {
        return addElectric;
    }

    public void setAddElectric(BigDecimal addElectric) {
        this.addElectric = addElectric;
    }

    public BigDecimal getElectricMoney() {
        return electricMoney;
    }

    public void setElectricMoney(BigDecimal electricMoney) {
        this.electricMoney = electricMoney;
    }

    public BigDecimal getRmbPrice() {
        return rmbPrice;
    }

    public void setRmbPrice(BigDecimal rmbPrice) {
        this.rmbPrice = rmbPrice;
    }

    public int getCoinType() {
        return coinType;
    }

    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }

    public String getFromChannel() {
        return fromChannel;
    }

    public void setFromChannel(String fromChannel) {
        this.fromChannel = fromChannel;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getComeDate() {
        return comeDate;
    }

    public void setComeDate(String comeDate) {
        this.comeDate = comeDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductManage() {
        return productManage;
    }

    public void setProductManage(String productManage) {
        this.productManage = productManage;
    }

    public String getManage() {
        return manage;
    }

    public void setManage(String manage) {
        this.manage = manage;
    }

    public String getProductManageDiscount() {
        return productManageDiscount;
    }

    public void setProductManageDiscount(String productManageDiscount) {
        this.productManageDiscount = productManageDiscount;
    }

    public String getProductElectric() {
        return productElectric;
    }

    public void setProductElectric(String productElectric) {
        this.productElectric = productElectric;
    }

    public String getRmbElectricPrice() {
        return rmbElectricPrice;
    }

    public void setRmbElectricPrice(String rmbElectricPrice) {
        this.rmbElectricPrice = rmbElectricPrice;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getProductValidity() {
        return productValidity;
    }

    public void setProductValidity(String productValidity) {
        this.productValidity = productValidity;
    }

    public String getProfitUsdt() {
        return profitUsdt;
    }

    public void setProfitUsdt(String profitUsdt) {
        this.profitUsdt = profitUsdt;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getElectricDiscount() {
        return electricDiscount;
    }

    public void setElectricDiscount(String electricDiscount) {
        this.electricDiscount = electricDiscount;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getIsReward() {
        return isReward;
    }

    public void setIsReward(String isReward) {
        this.isReward = isReward;
    }

    public String getBtcDiscount() {
        return btcDiscount;
    }

    public void setBtcDiscount(String btcDiscount) {
        this.btcDiscount = btcDiscount;
    }

    public String getHbtDiscount() {
        return hbtDiscount;
    }

    public void setHbtDiscount(String hbtDiscount) {
        this.hbtDiscount = hbtDiscount;
    }

    public String getElectricDicountMd5Format() {
        return electricDicountMd5Format;
    }

    public void setElectricDicountMd5Format(String electricDicountMd5Format) {
        this.electricDicountMd5Format = electricDicountMd5Format;
    }

    public String getEncapsulationDay() {
        return encapsulationDay;
    }

    public void setEncapsulationDay(String encapsulationDay) {
        this.encapsulationDay = encapsulationDay;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getExcision() {
        return excision;
    }

    public void setExcision(Integer excision) {
        this.excision = excision;
    }

    public void init(){
        setCreator(0L);
        setModifier(0L);
        setInvest(1);
        setDiscount(BigDecimal.ZERO);
        setTerminationDate(null);
        setTwelveSmstag(0);
        setOneSmstag(0);
        setEarnest(0);
        setBalancePayment(0);
        setReturnMoney(null);
        setElectric(null);
        setComeDate(null);
        setExpireDate(null);
        setManage(null);
        setProductManageDiscount(null);
        setProductElectric(null);
        setRmbElectricPrice(null);
        setSaleType(null);
        setProductValidity(null);
        setProfitUsdt(null);
        setChild(null);
        setToday(new Date());
        setShopCode(null);
        setElectricDiscount(null);
        setProfit(null);
        setIsReward(null);
        setBtcDiscount(null);
        setHbtDiscount(null);
        setElectricDicountMd5Format(null);
        setEncapsulationDay(null);
        setReward(null);
        setOrderType(0);
        setExcision(0);
    }
}
