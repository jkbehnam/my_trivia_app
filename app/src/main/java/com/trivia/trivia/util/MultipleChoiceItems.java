package com.trivia.trivia.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultipleChoiceItems {
    @SerializedName("answer")
    @Expose
    private String title;
    @SerializedName("state")
    @Expose
    private int correct;
    @SerializedName("ggg")
    @Expose
    private int state;
    public MultipleChoiceItems(String title1,int state){
        this.setState(state);
        title=title1;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
