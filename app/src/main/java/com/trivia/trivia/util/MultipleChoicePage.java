package com.trivia.trivia.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MultipleChoicePage extends Question{


    private ArrayList<MultipleChoiceItems> mpi;

    public MultipleChoicePage(String Q_title, String Q_type, String Q_lang, String Q_id, String Q_img,String Q_score,String Q_string) {
        super(Q_title, Q_type, Q_lang, Q_id, Q_img,Q_score,Q_string);
    }




    public ArrayList<MultipleChoiceItems> getMpi() {
        return mpi;
    }

    public void setMpi(ArrayList<MultipleChoiceItems> mpi) {
        this.mpi = mpi;
    }
}
