package com.example.demo.been;

import java.util.List;

public class Score {
    public int chart_id;
    public String img_url;
    public String type;
    public String title;
    public String artist;
    public List<RatingItem> ratingItems;

    public int getChart_id() {
        return chart_id;
    }

    public void setChart_id(int chart_id) {
        this.chart_id = chart_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<RatingItem> getRatingItems() {
        return ratingItems;
    }

    public void setRatingItems(List<RatingItem> ratingItems) {
        this.ratingItems = ratingItems;
    }
}
