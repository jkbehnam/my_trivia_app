package com.trivia.trivia.home.gameActivity.answerQuestion;

import com.trivia.trivia.util.MultipleChoicePage;

public interface IGames {
    void correcAnswer(int item);
    void wrongAnswer(int item);
    void setItems(MultipleChoicePage mpcp);
    public void  showLoading();
    public void hideLoading();
    public void setscore(String score);
    public void finishFrag();
    public void setLenght(String s);
}
