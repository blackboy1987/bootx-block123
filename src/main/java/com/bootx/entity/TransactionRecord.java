package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class TransactionRecord extends BaseEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    public Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member buyer;

    /**
     * 数量
     */
    @Column(precision = 27, scale = 18)
    @JsonView({ListView.class,ViewView.class})
    private BigDecimal jlbCount;

    /**
     * 价格
     */
    @Column(precision = 27, scale = 18)
    @JsonView({ListView.class,ViewView.class})
    private BigDecimal rmbAmount;

    /**
     * 手续费比例
     */
    @Column(precision = 27, scale = 18)
    private BigDecimal handlingFeesRate;

    /**
     * 手续费
     */
    @Column(precision = 27, scale = 18)
    private BigDecimal handlingFees;

    /**
     * 0:卖出
     * 1：买入
     */
    @JsonView({ListView.class,ViewView.class})
    private Integer type;

    /**
     * 0:待交易
     * 1:交易中
     * 2：待确认
     * 3：已确认
     * 4：已取消
     * 5：已拒绝
     */
    @JsonView({ListView.class,ViewView.class})
    private Integer status;

    @JsonView({ListView.class,ViewView.class})
    private String image;

    @JsonView({ListView.class,ViewView.class})
    private Date buyDate;

    @JsonView({ListView.class,ViewView.class})
    private Date confirmDate;

    /**
     * 过期时间
     */
    @JsonView({ListView.class,ViewView.class})
    private Date expire;

    public Member getSeller() {
        return seller;
    }

    public void setSeller(Member seller) {
        this.seller = seller;
    }

    public Member getBuyer() {
        return buyer;
    }

    public void setBuyer(Member buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getJlbCount() {
        return jlbCount;
    }

    public void setJlbCount(BigDecimal jlbCount) {
        this.jlbCount = jlbCount;
    }

    public BigDecimal getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(BigDecimal rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public BigDecimal getHandlingFeesRate() {
        return handlingFeesRate;
    }

    public void setHandlingFeesRate(BigDecimal handlingFeesRate) {
        this.handlingFeesRate = handlingFeesRate;
    }

    public BigDecimal getHandlingFees() {
        return handlingFees;
    }

    public void setHandlingFees(BigDecimal handlingFees) {
        this.handlingFees = handlingFees;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    @Transient
    @JsonView({ListView.class,ViewView.class})
    private String getBuyerName(){
        if(buyer!=null){
            return buyer.getUsername();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class,ViewView.class})
    private String getSellerName(){
        if(seller!=null){
            return seller.getUsername();
        }
        return null;
    }
}
