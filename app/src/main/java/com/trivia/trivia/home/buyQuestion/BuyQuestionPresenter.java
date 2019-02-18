package com.trivia.trivia.home.buyQuestion;

import android.widget.Toast;

public class BuyQuestionPresenter {
    BuyQuestionFragment buyQuestionFragment;
    BuyQuestionPresenter(BuyQuestionFragment buyQuestionFragment){
        this.buyQuestionFragment=buyQuestionFragment;
    }
    void getScore(){}
    void buyQuestion(int level,int type){
        Toast.makeText(buyQuestionFragment.getContext(), String.valueOf(level)+" "+String.valueOf(type), Toast.LENGTH_SHORT).show();
    }

}
