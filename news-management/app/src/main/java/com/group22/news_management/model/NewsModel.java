package com.group22.news_management.model;

import java.util.Date;

public class NewsModel {
    private long id;
    private long categoryId;
    private String thumbnail;
    private String title;
    private String content;
    private String description;
    private Date pubDate;
    private int numOfClicks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public int getNumOfClicks() {
        return numOfClicks;
    }

    public void setNumOfClicks(int numOfClicks) {
        this.numOfClicks = numOfClicks;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
