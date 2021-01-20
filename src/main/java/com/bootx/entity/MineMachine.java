
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 矿机
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MineMachine extends BaseEntity<Long> {

    /**
     * 类型
     */
    @JsonView({PageView.class})
    private Integer type;
    /**
     * 矿机名称
     */
    @JsonView({PageView.class})
    private String name;
    /**
     * 标签
     */
    @JsonView({PageView.class})
    private String tag;
    /**
     * 图片
     */
    @JsonView({PageView.class})
    private String icon;
    /**
     * 图片
     */
    private String images;
    /**
     *
     */
    private Integer saleType;
    /**
     * rmb价格
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal rmbPrice;
    /**
     * 价格
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal price;
    /**
     *
     */
    @JsonView({PageView.class})
    private Integer invest;


    /**
     * 算力(每日产出)
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal profit;

    /**
     * 每小时的算力(每小时产出)
     */
    @Column(precision = 27, scale = 12)
    private BigDecimal hourProfit;
    /**
     * rmb电费价格
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal rmbElectricPrice;
    /**
     * 电费
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal electric;
    /**
     * 电费折扣
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal electricDiscount;

    /**
     * 管理费
     */
    @JsonView({PageView.class})
    private Integer manage;
    /**
     * 管理费折扣
     */
    @JsonView({PageView.class})
    private Integer manageDiscount;

    /**
     * 每年算力
     */
    @JsonView({PageView.class})
    private Integer profitYear;

    /**
     * 限购数量
     */
    @Column(name ="limits")
    @JsonView({PageView.class})
    private Integer limit;
    /**
     * 默认购买数量
     */
    private Integer defaultBuyNumber;
    /**
     * 是否上线
     */
    @JsonView({PageView.class})
    private Boolean isMarketable;
    /**
     * 是否特殊
     */
    @JsonView({PageView.class})
    private Boolean isSpecials;
    /**
     * 特殊的icon
     */
    @JsonView({PageView.class})
    private String specialsIcon;
    /**
     * 月销售量
     */
    @JsonView({PageView.class})
    private Integer monthSales;

    /**
     *
     */
    @JsonView({PageView.class})
    private Boolean profitDay;
    /**
     *
     */
    @JsonView({PageView.class})
    private String returnProfitDay;
    /**
     * 周销售量
     */
    @JsonView({PageView.class})
    private Integer weekSales;
    /**
     * 总销售量
     */
    @JsonView({PageView.class})
    private Integer sales;
    /**
     * 总费用
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal money;
    /**
     * 库存数量
     */
    @JsonView({PageView.class})
    private Long stock;
    /**
     * 是否有库存
     */
    @JsonView({PageView.class})
    private Boolean isStock;
    /**
     * 过期时间
     */
    @JsonView({PageView.class})
    private String expirationDate;
    /**
     *
     */
    @JsonView({PageView.class})
    private String validity;
    /**
     * 是否推荐
     */
    @JsonView({PageView.class})
    private Boolean isRecommend;
    /**
     * 对应的新闻id
     */
    @JsonView({PageView.class})
    private Integer newsId;
    /**
     *
     */
    @JsonView({PageView.class})
    private String investTime;
    /**
     * btc 折扣
     */
    @JsonView({PageView.class})
    private Integer btcDiscount;
    /**
     * hbt 折扣
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal hbtDiscount;
    /**
     * hbt 折扣类型
     */
    @JsonView({PageView.class})
    private Integer hbtDiscountType;
    /**
     * 最大算力
     */
    @JsonView({PageView.class})
    private String maxLimit;
    /**
     * 收益
     */
    @JsonView({PageView.class})
    private String earnest;
    /**
     * 返点
     */
    @JsonView({PageView.class})
    private String returnRate;
    /**
     *
     */
    @JsonView({PageView.class})
    private String interest;
    /**
     *
     */
    @JsonView({PageView.class})
    private String surplus;
    /**
     *
     */
    @JsonView({PageView.class})
    private String maxQouta;
    /**
     *
     */
    @JsonView({PageView.class})
    private String isReward;
    /**
     *
     */
    @JsonView({PageView.class})
    private String hierarchy;

    /**
     * 用什么币才能购买
     */
    @JsonView({PageView.class})
    private Integer coinType;
    /**
     *
     */
    @JsonView({PageView.class})
    @Column(precision = 27, scale = 12)
    private BigDecimal exchangeRate;
    /**
     * 产品id
     */
    @JsonView({PageView.class})
    private String productId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public BigDecimal getRmbPrice() {
        return rmbPrice;
    }

    public void setRmbPrice(BigDecimal rmbPrice) {
        this.rmbPrice = rmbPrice;
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

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getHourProfit() {
        return hourProfit;
    }

    public void setHourProfit(BigDecimal hourProfit) {
        this.hourProfit = hourProfit;
    }

    public BigDecimal getRmbElectricPrice() {
        return rmbElectricPrice;
    }

    public void setRmbElectricPrice(BigDecimal rmbElectricPrice) {
        this.rmbElectricPrice = rmbElectricPrice;
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

    public Integer getManage() {
        return manage;
    }

    public void setManage(Integer manage) {
        this.manage = manage;
    }

    public Integer getManageDiscount() {
        return manageDiscount;
    }

    public void setManageDiscount(Integer manageDiscount) {
        this.manageDiscount = manageDiscount;
    }

    public Integer getProfitYear() {
        return profitYear;
    }

    public void setProfitYear(Integer profitYear) {
        this.profitYear = profitYear;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getDefaultBuyNumber() {
        return defaultBuyNumber;
    }

    public void setDefaultBuyNumber(Integer defaultBuyNumber) {
        this.defaultBuyNumber = defaultBuyNumber;
    }

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Boolean getIsSpecials() {
        return isSpecials;
    }

    public void setIsSpecials(Boolean isSpecials) {
        this.isSpecials = isSpecials;
    }

    public String getSpecialsIcon() {
        return specialsIcon;
    }

    public void setSpecialsIcon(String specialsIcon) {
        this.specialsIcon = specialsIcon;
    }

    public Integer getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    public Boolean getProfitDay() {
        return profitDay;
    }

    public void setProfitDay(Boolean profitDay) {
        this.profitDay = profitDay;
    }

    public String getReturnProfitDay() {
        return returnProfitDay;
    }

    public void setReturnProfitDay(String returnProfitDay) {
        this.returnProfitDay = returnProfitDay;
    }

    public Integer getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(Integer weekSales) {
        this.weekSales = weekSales;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getStock() {
        return stock;
    }


    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Boolean getIsStock() {
        return isStock;
    }

    public void setIsStock(Boolean isStock) {
        this.isStock = isStock;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public Integer getBtcDiscount() {
        return btcDiscount;
    }

    public void setBtcDiscount(Integer btcDiscount) {
        this.btcDiscount = btcDiscount;
    }

    public BigDecimal getHbtDiscount() {
        return hbtDiscount;
    }

    public void setHbtDiscount(BigDecimal hbtDiscount) {
        this.hbtDiscount = hbtDiscount;
    }

    public Integer getHbtDiscountType() {
        return hbtDiscountType;
    }

    public void setHbtDiscountType(Integer hbtDiscountType) {
        this.hbtDiscountType = hbtDiscountType;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getEarnest() {
        return earnest;
    }

    public void setEarnest(String earnest) {
        this.earnest = earnest;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getMaxQouta() {
        return maxQouta;
    }

    public void setMaxQouta(String maxQouta) {
        this.maxQouta = maxQouta;
    }

    public String getIsReward() {
        return isReward;
    }

    public void setIsReward(String isReward) {
        this.isReward = isReward;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @PrePersist
    public void preSave(){
        if(profit==null){
            profit = BigDecimal.ZERO;
            hourProfit = profit.divide(new BigDecimal(24),10, RoundingMode.CEILING);
        }
    }

    @PreUpdate
    public void preUpdate(){
        if(profit==null){
            profit = BigDecimal.ZERO;
            hourProfit = profit.divide(new BigDecimal(24),10,RoundingMode.CEILING);
        }
    }

}