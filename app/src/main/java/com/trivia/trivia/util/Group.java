package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Group implements Serializable {
    @SerializedName("g_id")
    @Expose
    private String g_id;
    @SerializedName("g_name")
    @Expose
    private String g_name;
    @SerializedName("g_score")
    @Expose
    private String g_score;
    @SerializedName("g_start_time")
    @Expose
    private String g_start_time;
    @SerializedName("g_end_time")
    @Expose
    private String g_end_time;
    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_score() {
        return g_score;
    }

    public void setG_score(String g_score) {
        this.g_score = g_score;
    }

    public String getG_start_time() {
        return g_start_time;
    }

    public void setG_start_time(String g_start_time) {
        this.g_start_time = g_start_time;
    }

    public String getG_end_time() {
        return g_end_time;
    }

    public void setG_end_time(String g_end_time) {
        this.g_end_time = g_end_time;
    }
}
