package com.trivia.trivia.util;

public class MultipleChoiceItems {
    private String title;
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
}
