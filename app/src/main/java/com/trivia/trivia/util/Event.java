package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable {
    @SerializedName("e_id")
    @Expose
    private
    String e_id;
    @SerializedName("e_name")
    @Expose
    private
    String e_name;
    @SerializedName("e_type")
    @Expose
    private
    String e_type;
    @SerializedName("e_country")
    @Expose
    private
    String e_country;
    @SerializedName("e_province")
    @Expose
    private
    String e_province;
    @SerializedName("e_district")
    @Expose
    private
    String e_district;
    @SerializedName("e_city")
    @Expose
    private
    String e_city;
    @SerializedName("e_lang")
    @Expose
    private
    String e_lang;
    @SerializedName("e_start_type")
    @Expose
    private
    String e_start_type;
    @SerializedName("e_start_time")
    @Expose
    private
    String e_start_time;
    @SerializedName("e_sex")
    @Expose
    private
    String e_sex;
    @SerializedName("e_img")
    @Expose
    private
    String e_img;

    @SerializedName("e_duration")
    @Expose
    private
    String e_duration;

    @SerializedName("e_inflation_rate")
    @Expose
    private
    String e_inflation_rate;

    @SerializedName("e_first_score")
    @Expose
    private
    String e_first_score;

    @SerializedName("e_price")
    @Expose
    private
    String e_price;

    @SerializedName("e_description")
    @Expose
    private
    String e_description;


    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_type() {
        return e_type;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public String getE_country() {
        return e_country;
    }

    public void setE_country(String e_country) {
        this.e_country = e_country;
    }

    public String getE_province() {
        return e_province;
    }

    public void setE_province(String e_province) {
        this.e_province = e_province;
    }

    public String getE_district() {
        return e_district;
    }

    public void setE_district(String e_district) {
        this.e_district = e_district;
    }

    public String getE_city() {
        return e_city;
    }

    public void setE_city(String e_city) {
        this.e_city = e_city;
    }

    public String getE_lang() {
        return e_lang;
    }

    public void setE_lang(String e_lang) {
        this.e_lang = e_lang;
    }

    public String getE_start_type() {
        return e_start_type;
    }

    public void setE_start_type(String e_start_type) {
        this.e_start_type = e_start_type;
    }

    public String getE_start_time() {
        return e_start_time;
    }

    public void setE_start_time(String e_start_time) {
        this.e_start_time = e_start_time;
    }

    public String getE_sex() {
        return e_sex;
    }

    public void setE_sex(String e_sex) {
        this.e_sex = e_sex;
    }

    public String getE_img() {
        return e_img;
    }

    public void setE_img(String e_img) {
        this.e_img = e_img;
    }

    public String getE_duration() {
        return e_duration;
    }

    public void setE_duration(String e_duration) {
        this.e_duration = e_duration;
    }

    public String getE_inflation_rate() {
        return e_inflation_rate;
    }

    public void setE_inflation_rate(String e_inflation_rate) {
        this.e_inflation_rate = e_inflation_rate;
    }

    public String getE_first_score() {
        return e_first_score;
    }

    public void setE_first_score(String e_first_score) {
        this.e_first_score = e_first_score;
    }

    public String getE_price() {
        return e_price;
    }

    public void setE_price(String e_price) {
        this.e_price = e_price;
    }

    public String getE_description() {
        return e_description;
    }

    public void setE_description(String e_description) {
        this.e_description = e_description;
    }
}
