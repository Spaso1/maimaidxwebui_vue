package com.example.demo.been;

import java.util.List;

public class RatingData {
    private List<RatingItem> newSongs;
    private List<RatingItem> bestSongs;
    private List<RatingItem> newCandidates;
    private List<RatingItem> bestCandidates;

    // Getters and Setters
    public List<RatingItem> getNewSongs() {
        return newSongs;
    }

    public void setNewSongs(List<RatingItem> newSongs) {
        this.newSongs = newSongs;
    }

    public List<RatingItem> getBestSongs() {
        return bestSongs;
    }

    public void setBestSongs(List<RatingItem> bestSongs) {
        this.bestSongs = bestSongs;
    }

    public List<RatingItem> getNewCandidates() {
        return newCandidates;
    }

    public void setNewCandidates(List<RatingItem> newCandidates) {
        this.newCandidates = newCandidates;
    }

    public List<RatingItem> getBestCandidates() {
        return bestCandidates;
    }

    public void setBestCandidates(List<RatingItem> bestCandidates) {
        this.bestCandidates = bestCandidates;
    }
}
