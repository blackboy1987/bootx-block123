package com.bootx.entity;

import javax.persistence.Entity;

@Entity
public class Announcement extends BaseEntity<Long>{

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
