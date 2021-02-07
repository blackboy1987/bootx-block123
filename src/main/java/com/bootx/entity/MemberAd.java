package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员发布的广告
 */
@Entity
public class MemberAd extends BaseEntity<Long>{

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    /**
     * 广告内容
     */
    @NotEmpty
    @Length(max = 1000)
    @Column(nullable = false)
    private String content;

    /**
     * 广告图片
     */
    @Convert(converter = ImageConverter.class)
    private List<String> images;

    @NotNull
    @Column(nullable = false,precision = 27, scale = 12)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Boolean isShow;

    /**
     * 点击次数
     */
    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer times;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer remainTimes;

    /**
     * 0:JLB
     * 1:CNY
     */
    @NotNull
    @Column(nullable = false)
    private Integer coinType;

    @OneToOne(mappedBy = "memberAd",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private MemberAdStatistics statistics;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public MemberAdStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(MemberAdStatistics statistics) {
        this.statistics = statistics;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public Integer getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(Integer remainTimes) {
        this.remainTimes = remainTimes;
    }

    @Convert
    public static class ImageConverter extends BaseAttributeConverter<List<String>>{

    }
}
