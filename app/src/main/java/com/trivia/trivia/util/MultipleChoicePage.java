package com.trivia.trivia.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MultipleChoicePage {
    private String title;
    private String photo;
    private ArrayList<MultipleChoiceItems> mpi;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<MultipleChoiceItems> getMpi() {
        return mpi;
    }

    public void setMpi(ArrayList<MultipleChoiceItems> mpi) {
        this.mpi = mpi;
    }
}
