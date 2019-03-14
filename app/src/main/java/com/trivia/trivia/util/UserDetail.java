package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetail implements Serializable {
    @SerializedName("u_id")
    @Expose
    private int u_id;
    @SerializedName("ud_email")
    @Expose
    private String ud_email;
    @SerializedName("ud_birthday")
    @Expose
    private String ud_birthday;
    @SerializedName("ud_sex")
    @Expose
    private String ud_sex;
    @SerializedName("ud_national_code")
    @Expose
    private String ud_national_code;
    @SerializedName("ud_country")
    @Expose
    private String ud_country;
    @SerializedName("ud_city")
    @Expose
    private String ud_city;
    @SerializedName("ud_grade")
    @Expose
    private String ud_grade;
    @SerializedName("ud_school")
    @Expose
    private String ud_school;
    @SerializedName("ud_province")
    @Expose
    private String ud_province;


    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getUd_email() {
        return ud_email;
    }

    public void setUd_email(String ud_email) {
        this.ud_email = ud_email;
    }

    public String getUd_birthday() {
        return ud_birthday;
    }

    public void setUd_birthday(String ud_birthday) {
        this.ud_birthday = ud_birthday;
    }

    public String getUd_sex() {
        return ud_sex;
    }

    public void setUd_sex(String ud_sex) {
        this.ud_sex = ud_sex;
    }

    public String getUd_national_code() {
        return ud_national_code;
    }

    public void setUd_national_code(String ud_national_code) {
        this.ud_national_code = ud_national_code;
    }

    public String getUd_country() {
        return ud_country;
    }

    public void setUd_country(String ud_country) {
        this.ud_country = ud_country;
    }

    public String getUd_city() {
        return ud_city;
    }

    public void setUd_city(String ud_city) {
        this.ud_city = ud_city;
    }

    public String getUd_grade() {
        return ud_grade;
    }

    public void setUd_grade(String ud_grade) {
        this.ud_grade = ud_grade;
    }

    public String getUd_school() {
        return ud_school;
    }

    public void setUd_school(String ud_school) {
        this.ud_school = ud_school;
    }

    public String getUd_province() {
        return ud_province;
    }

    public void setUd_province(String ud_province) {
        this.ud_province = ud_province;
    }
}
