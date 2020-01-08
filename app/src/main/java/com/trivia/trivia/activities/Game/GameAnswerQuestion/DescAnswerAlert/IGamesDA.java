package com.trivia.trivia.activities.Game.GameAnswerQuestion.DescAnswerAlert;


public interface IGamesDA {
    void correcAnswer(int item);
    void wrongAnswer(int item);

    void  showLoading();
    void hideLoading();
    void setscore(String score);
    void finishFrag();

    void showanimation();
}
