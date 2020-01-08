package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Member implements Serializable {
    @SerializedName("u_id")
    @Expose
    private String u_id;
    @SerializedName("u_name")
    @Expose
    private String u_name;
    @SerializedName("u_correct_answer")
    @Expose
    private String u_correct_answer;
    @SerializedName("u_wrong_answer")
    @Expose
    private String u_wrong_answer;
    @SerializedName("u_score")
    @Expose
    private String u_score;
public Member(String u_id, String u_name, String u_correct_answer, String u_wrong_answer, String u_score){
    this.u_id=u_id;
    this.u_name=u_name;
    this.u_correct_answer=u_correct_answer;
    this.u_wrong_answer=u_wrong_answer;
    this.u_score=u_score;
}


    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_correct_answer() {
        return u_correct_answer;
    }

    public void setU_correct_answer(String u_correct_answer) {
        this.u_correct_answer = u_correct_answer;
    }

    public String getU_wrong_answer() {
        return u_wrong_answer;
    }

    public void setU_wrong_answer(String u_wrong_answer) {
        this.u_wrong_answer = u_wrong_answer;
    }

    public String getU_score() {
        return u_score;
    }

    public void setU_score(String u_score) {
        this.u_score = u_score;
    }
}
