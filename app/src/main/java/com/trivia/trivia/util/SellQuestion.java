package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellQuestion implements Serializable {
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
    @SerializedName("g_id")
    @Expose
    private String seller;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date")
    @Expose
    private String time;

public SellQuestion(){}
    public SellQuestion(String id, String q_id, String q_title, String q_type, String q_level, String q_score, String q_img, String q_package, String q_string, String seller, String price, String time) {
        this.id = id;
        Q_id = q_id;
        Q_title = q_title;
        Q_type = q_type;
        Q_level = q_level;
        Q_score = q_score;
        Q_img = q_img;
        Q_package = q_package;
        Q_string = q_string;
        this.seller = seller;
        this.price = price;
        this.time = time;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
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

    public String getQ_score() {
        return Q_score;
    }

    public void setQ_score(String q_score) {
        Q_score = q_score;
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

    public String getQ_string() {
        return Q_string;
    }

    public void setQ_string(String q_string) {
        Q_string = q_string;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
