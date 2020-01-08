package com.trivia.trivia.activities.Game.GameGamerQuestion;

import com.trivia.trivia.util.Question;

import java.util.ArrayList;

public interface Iquestionview {
    void setItems(ArrayList<Question> questionsList);
void removeitem(int position);
    void  showLoading();
    void hideLoading();
    void dimissSellAlert();
}
