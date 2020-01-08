package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Question implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("q_id")
    @Expose
    private String Q_id;

    @SerializedName("q_title")
    @Expose
    private String Q_title;
    @SerializedName("q_type")
    @Expose
    private String Q_type;
    @SerializedName("q_level")
    @Expose
    private String Q_level;
    @SerializedName("q_score")
    @Expose
    private
    String Q_score;
    @SerializedName("q_img")
    @Expose
    private String Q_img;
    @SerializedName("q_package")
    @Expose
    private String Q_package;
    @SerializedName("q_string")
    @Expose
    private String Q_string;

    @SerializedName("q_try_num")
    @Expose
    private int Q_try_num;
    @SerializedName("q_state")
    @Expose
    private int q_state;


    public Question(String Q_title, String Q_type, String Q_level, String Q_id, String Q_img,String Q_score,String Q_string) {
        setQ_title(Q_title);
        setQ_type(Q_type);
        setQ_level(Q_level);
        setQ_id(Q_id);
        setQ_img(Q_img);
        setQ_score(Q_score);
        setQ_string(Q_string);

    }
    public Question() {

    }

    public String getQ_title() {
        return Q_title;
    }

    public void setQ_title(String q_title) {
        Q_title = q_title;
    }

    public String getQ_type() {
        return Q_type;
    }

    public void setQ_type(String q_type) {
        Q_type = q_type;
    }

    public String getQ_level() {
        return Q_level;
    }

    public void setQ_level(String q_level) {
        Q_level = q_level;
    }

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }

    public String getQ_img() {
        return Q_img;
    }

    public void setQ_img(String q_img) {
        Q_img = q_img;
    }

    public String getQ_package() {
        return Q_package;
    }

    public void setQ_package(String q_package) {
        Q_package = q_package;
    }


    public String getQ_score() {
        return Q_score;
    }

    public void setQ_score(String q_score) {
        Q_score = q_score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQ_string() {
        return Q_string;
    }

    public void setQ_string(String q_string) {
        Q_string = q_string;
    }


    public int getQ_try_num() {
        return Q_try_num;
    }

    public void setQ_try_num(int q_try_num) {
        Q_try_num = q_try_num;
    }

    public int getQ_state() {
        return q_state;
    }

    public void setQ_state(int q_state) {
        this.q_state = q_state;
    }
}
