package com.example.demo.been;

public class RatingItem {
    private int level_id;
    private String level_String;
    private double score;
    private String music_name;
    private String dx_score;
    private int comboType;
    private int syncType;
    private int score_type;
    private int type;

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

    public int getComboType() {
        return comboType;
    }

    public void setComboType(int comboType) {
        this.comboType = comboType;
    }

    public int getSyncType() {
        return syncType;
    }

    public void setSyncType(int syncType) {
        this.syncType = syncType;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }
}
