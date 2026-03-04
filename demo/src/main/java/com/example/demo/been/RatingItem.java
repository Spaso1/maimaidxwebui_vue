package com.example.demo.been;

public class RatingItem {
    private int chart_id;
    private int level_id;
    private String level_String;
    private double score;
    private String music_name;
    private String dx_score;
    private int combo_type;
    private int sync_type;
    private int score_type;
    private int type;
    private String genre;

    private int playCount;
    private String lastPlayDate;

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getLastPlayDate() {
        return lastPlayDate;
    }

    public void setLastPlayDate(String lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }

    public int getChart_id() {
        return chart_id;
    }

    public void setChart_id(int chart_id) {
        this.chart_id = chart_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // Getters and Setters
    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getLevel_String() {
        return level_String;
    }

    public void setLevel_String(String level_String) {
        this.level_String = level_String;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getDx_score() {
        return dx_score;
    }

    public void setDx_score(String dx_score) {
        this.dx_score = dx_score;
    }

    public int getCombo_type() {
        return combo_type;
    }

    public void setCombo_type(int combo_type) {
        this.combo_type = combo_type;
    }

    public int getSync_type() {
        return sync_type;
    }

    public void setSync_type(int sync_type) {
        this.sync_type = sync_type;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
