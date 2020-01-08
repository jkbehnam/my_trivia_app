package com.trivia.trivia.activities.Game.GameAnswerQuestion.MultipleChoiceAlert;

import com.trivia.trivia.util.MultipleChoicePage;

public interface IGamesMC {
    void correcAnswer(int item);
    void wrongAnswer(int item);
    void setItems(MultipleChoicePage mpcp);
    void  showLoading();
    void hideLoading();
    void setscore(String score);
    void finishFrag();

}
