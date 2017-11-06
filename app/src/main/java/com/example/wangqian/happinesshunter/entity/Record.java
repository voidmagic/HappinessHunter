package com.example.wangqian.happinesshunter.entity;


public class Record {
    // unique id in database
    private Long id;

    // create time
    private Long createTime;

    // emotion strength, from 0-5, 0 for normal, 5 for very happy.
    private Integer strength;

    // record content
    private String content;

    public Record() {}



    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStrength() {
        return strength;
    }

    public Long getId() {
        return id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }
}
