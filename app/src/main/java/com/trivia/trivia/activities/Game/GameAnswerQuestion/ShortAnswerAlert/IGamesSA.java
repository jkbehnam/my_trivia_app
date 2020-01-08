package com.trivia.trivia.activities.Game.GameAnswerQuestion.ShortAnswerAlert;


public interface IGamesSA {
    void correcAnswer(int item);
    void wrongAnswer(int item);

    void  showLoading();
    void hideLoading();
    void setscore(String score);
    void finishFrag();
    void setLenght(String s);
    void showanimation();
}
