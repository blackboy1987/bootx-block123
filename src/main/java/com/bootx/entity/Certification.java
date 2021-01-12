package com.bootx.entity;


import com.bootx.common.BaseAttributeConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Certification extends BaseEntity<Long>{

    private Long userId;

    private String name;

    private String certNo;

    @Convert(converter = CertPhonConverter.class)
    private List<String> certPhoto = new ArrayList<>();

    /**
     * 0:审核中
     * 1： 审核通过
     * 2： 审核失败
     */
    private Integer state;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public List<String> getCertPhoto() {
        return certPhoto;
    }

    public void setCertPhoto(List<String> certPhoto) {
        this.certPhoto = certPhoto;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public static class CertPhonConverter extends BaseAttributeConverter<List<String>>{

    }
}
