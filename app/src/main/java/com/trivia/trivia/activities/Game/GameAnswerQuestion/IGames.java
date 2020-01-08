package com.trivia.trivia.activities.Game.GameAnswerQuestion;

import com.trivia.trivia.util.MultipleChoicePage;

public interface IGames {
    void correcAnswer(int item);
    void wrongAnswer(int item);
    void setItems(MultipleChoicePage mpcp);
    void  showLoading();
    void hideLoading();
    void setscore(String score);
    void finishFrag();
    void setLenght(String s);
    void showanimation();
}
