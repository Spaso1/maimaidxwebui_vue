package com.example.demo.been;

import java.util.List;
import java.util.Map;

public class MusicData {
    private Map<String, List<RatingItem>> songs;
    private int total;
    private MusicStatus musicStatus;
    private List<String> genreOrder;

    public Map<String, List<RatingItem>> getSongs() {
        return songs;
    }

    public void setSongs(Map<String, List<RatingItem>> songs) {
        this.songs = songs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MusicStatus getMusicStatus() {
        return musicStatus;
    }

    public void setMusicStatus(MusicStatus musicStatus) {
        this.musicStatus = musicStatus;
    }

    public List<String> getGenreOrder() {
        return genreOrder;
    }

    public void setGenreOrder(List<String> genreOrder) {
        this.genreOrder = genreOrder;
    }
}
