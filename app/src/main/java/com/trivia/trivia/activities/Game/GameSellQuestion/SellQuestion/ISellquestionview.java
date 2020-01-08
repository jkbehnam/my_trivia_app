package com.trivia.trivia.activities.Game.GameSellQuestion.SellQuestion;

import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.SellQuestion;

import java.util.ArrayList;

public interface ISellquestionview {
    void setItems(ArrayList<SellQuestion> questionsList);
void removeitem(int position);
    void  showLoading();
    void hideLoading();
    void dimissSellAlert();
}
