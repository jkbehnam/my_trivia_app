package com.trivia.trivia.util;

public class list_item {
    private String name;
    private String img;
    private String content;
public list_item(String name, String img, String content){
    this.name=name;
    this.img=img;
    this.content=content;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
