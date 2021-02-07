package com.bootx.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class MemberAdStatistics extends BaseEntity<Long>{

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,updatable = false,unique = true)
    private MemberAd memberAd;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long readCount;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long commentsCount;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long shareCount;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long praiseCount;

    public MemberAd getMemberAd() {
        return memberAd;
    }

    public void setMemberAd(MemberAd memberAd) {
        this.memberAd = memberAd;
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public Long getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Long praiseCount) {
        this.praiseCount = praiseCount;
    }
}
