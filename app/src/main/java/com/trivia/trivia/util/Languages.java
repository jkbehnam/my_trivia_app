package com.trivia.trivia.util;

public class Languages {
    private String name;
    private int image;
    private String code;
public Languages(String name1,String code1,int image1){
    name=name1;
    image=image1;
    code=code1;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
