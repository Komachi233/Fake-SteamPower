package com.fdzc.oracle0.bean;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Integer gid;
    private String name;
    private String dev;
    private String pub;
    private Integer price;
    private Integer discount;
    private String summary;
    private String pubDate;
    private String mainImg;
    private Boolean status;
    private List<String> tag;

    public Game(Integer gid, String name, String dev, String pub, Integer price, Integer discount, String summary, String pubDate, String mainImg, Boolean status) {
        this.gid = gid;
        this.name = name;
        this.dev = dev;
        this.pub = pub;
        this.price = price;
        this.discount = discount;
        this.summary = summary;
        this.pubDate = pubDate;
        this.mainImg = mainImg;
        this.status = status;
    }

    public Game(Integer gid, String name, String dev, String pub, Integer price, Integer discount, String summary, String pubDate, String mainImg, Boolean status, List<String> tag) {
        this.gid = gid;
        this.name = name;
        this.dev = dev;
        this.pub = pub;
        this.price = price;
        this.discount = discount;
        this.summary = summary;
        this.pubDate = pubDate;
        this.mainImg = mainImg;
        this.status = status;
        this.tag = new ArrayList<String>(tag);
    }

    public Game() {
    }

    public Integer getGid() {
        return gid;
    }

    public List<String> getTag() {
        return new ArrayList<String>(this.tag);
    }

    public void setTag(List<String> tag) {
        this.tag = new ArrayList<String>(tag);
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
