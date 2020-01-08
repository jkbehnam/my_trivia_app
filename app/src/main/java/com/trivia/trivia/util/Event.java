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
    @SerializedName("e_reg_deadline")
    @Expose
    private  String e_reg_deadline;
    @SerializedName("e_type")
    @Expose
    private
    String e_type;
    @SerializedName("e_lang")
    @Expose
    private
    String e_lang;
    @SerializedName("e_sex")
    @Expose
    private
    String e_sex;
    @SerializedName("e_img")
    @Expose
    private
    String e_img;
    @SerializedName("e_price")
    @Expose
    private
    String e_price;
    @SerializedName("e_description")
    @Expose
    private
    String e_description;
    @SerializedName("e_age")
    @Expose
    private
    String e_age;
    @SerializedName("e_grade")
    @Expose
    private
    String e_grade;
    @SerializedName("e_major")
    @Expose
    private
    String e_major;

    @SerializedName("e_reg_max_num")
    @Expose
    private
    String e_Reg_max_num;
    @SerializedName("e_reg_num")
    @Expose
    private
    String e_Reg_num;
    @SerializedName("e_game_type")
    @Expose
    private
    String e_game_type;
    @SerializedName("e_start_time")
    @Expose
    private
    String e_start_time;
    @SerializedName("e_end_time")
    @Expose
    private  String e_end_time;
    @SerializedName("e_duration")
    @Expose
    private
    String e_duration;


    @SerializedName("e_custom_exit")
    @Expose
    private String e_custom_exit;

    @SerializedName("e_inflation_rate")
    @Expose
    private
    String e_inflation_rate;
    @SerializedName("e_first_score")
    @Expose
    private
    String e_first_score;
    @SerializedName("e_group_members")
    @Expose
    private String e_group_members;

    @SerializedName("e_gather_start_time")
    @Expose
    private String e_gather_start_time;
    @SerializedName("e_gather_end_time")
    @Expose
    private String e_gather_end_time;
    @SerializedName("e_inflation_fix_period")
    @Expose
    private String e_inflation_fix_period;
    @SerializedName("e_ranking_time")
    @Expose
    private String e_ranking_time;

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
    @SerializedName("e_address")
    @Expose
    private
    String e_address;
    @SerializedName("g_end_time")
    @Expose
    private
    String g_end_time;
    @SerializedName("g_start_time")
    @Expose
    private
    String g_start_time;
    @SerializedName("g_checkout")
    @Expose
    private
    String g_checkout;
    @SerializedName("e_password")
    @Expose
    private
    String e_password;









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

    public String getE_group_members() {
        return e_group_members;
    }

    public void setE_group_members(String e_group_members) {
        this.e_group_members = e_group_members;
    }

    public String getE_end_time() {
        return e_end_time;
    }

    public void setE_end_time(String e_end_time) {
        this.e_end_time = e_end_time;
    }

    public String getE_reg_deadline() {
        return e_reg_deadline;
    }

    public void setE_reg_deadline(String e_reg_deadline) {
        this.e_reg_deadline = e_reg_deadline;
    }

    public String getE_age() {
        return e_age;
    }

    public void setE_age(String e_age) {
        this.e_age = e_age;
    }


    public String getE_major() {
        return e_major;
    }

    public void setE_major(String e_major) {
        this.e_major = e_major;
    }

    public String getE_Reg_max_num() {
        return e_Reg_max_num;
    }

    public void setE_Reg_max_num(String e_Reg_max_num) {
        this.e_Reg_max_num = e_Reg_max_num;
    }

    public String getE_Reg_num() {
        return e_Reg_num;
    }

    public void setE_Reg_num(String e_Reg_num) {
        this.e_Reg_num = e_Reg_num;
    }

    public String getE_game_type() {
        return e_game_type;
    }

    public void setE_game_type(String e_game_type) {
        this.e_game_type = e_game_type;
    }

    public String getE_address() {
        return e_address;
    }

    public void setE_address(String e_address) {
        this.e_address = e_address;
    }

    public String getE_grade() {
        return e_grade;
    }

    public void setE_grade(String e_grade) {
        this.e_grade = e_grade;
    }

    public String getE_custom_exit() {
        return e_custom_exit;
    }

    public void setE_custom_exit(String e_custom_exit) {
        this.e_custom_exit = e_custom_exit;
    }

    public String getE_gather_start_time() {
        return e_gather_start_time;
    }

    public void setE_gather_start_time(String e_gather_start_time) {
        this.e_gather_start_time = e_gather_start_time;
    }

    public String getE_gather_end_time() {
        return e_gather_end_time;
    }

    public void setE_gather_end_time(String e_gather_end_time) {
        this.e_gather_end_time = e_gather_end_time;
    }

    public String getE_inflation_fix_period() {
        return e_inflation_fix_period;
    }

    public void setE_inflation_fix_period(String e_inflation_fix_period) {
        this.e_inflation_fix_period = e_inflation_fix_period;
    }

    public String getE_ranking_time() {
        return e_ranking_time;
    }

    public void setE_ranking_time(String e_ranking_time) {
        this.e_ranking_time = e_ranking_time;
    }


    public String getG_end_time() {
        return g_end_time;
    }

    public void setG_end_time(String g_end_time) {
        this.g_end_time = g_end_time;
    }

    public String getG_checkout() {
        return g_checkout;
    }

    public void setG_checkout(String g_checkout) {
        this.g_checkout = g_checkout;
    }

    public String getG_start_time() {
        return g_start_time;
    }

    public void setG_start_time(String g_start_time) {
        this.g_start_time = g_start_time;
    }

    public String getE_password() {
        return e_password;
    }

    public void setE_password(String e_password) {
        this.e_password = e_password;
    }
}
