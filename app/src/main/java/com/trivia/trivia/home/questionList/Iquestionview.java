package com.trivia.trivia.home.questionList;

import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Question;

import java.util.ArrayList;

public interface Iquestionview {
    public void setItems(ArrayList<Question> questionsList);

    public void  showLoading();
    public void hideLoading();
}
