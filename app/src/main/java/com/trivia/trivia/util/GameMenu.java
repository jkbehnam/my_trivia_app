package com.trivia.trivia.util;

public class GameMenu {
    private String title;
    private String img;

    public GameMenu(String title1,String img1){
        title=title1;
        img=img1;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
