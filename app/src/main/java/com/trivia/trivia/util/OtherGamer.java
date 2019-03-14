package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherGamer implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("member")
    @Expose
    private String members;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("answerd_q")
    @Expose
    private String answerd_q;

    public OtherGamer(String _name, String _phone_number, String _score, String _answerd_q ){
        setName(_name);
        setScore(_phone_number);
        setMembers(_score);
        setAnswerd_q(_answerd_q);
    }
    public OtherGamer( ){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
    public String print_gamer_card(){

        String card=this.getName()+"/"+this.getScore()+"/"+ String.valueOf(this.members);
           return card;
    }

    public String getAnswerd_q() {
        return answerd_q;
    }

    public void setAnswerd_q(String answerd_q) {
        this.answerd_q = answerd_q;
    }
}
