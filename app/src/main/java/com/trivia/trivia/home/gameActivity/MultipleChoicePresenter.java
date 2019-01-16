package com.trivia.trivia.home.gameActivity;

import android.widget.Toast;

import com.trivia.trivia.util.MultipleChoiceItems;
import com.trivia.trivia.util.MultipleChoicePage;

import java.util.ArrayList;

public class MultipleChoicePresenter {
    IGames iGames;

    public MultipleChoicePresenter(IGames iGames2) {
        this.iGames = iGames2;
    }



    public void settiems(String qId) {
        MultipleChoicePage mpcp = new MultipleChoicePage("سوال؟؟","","","","");


        ArrayList<MultipleChoiceItems> mci = new ArrayList<>();
        mci.add(new MultipleChoiceItems("گزینه 1",0));
        mci.add(new MultipleChoiceItems("گزینه 2",0));
        mci.add(new MultipleChoiceItems("گزینه 3",0));
        mci.add(new MultipleChoiceItems("گزینه 4",0));
        mpcp.setMpi(mci);
        iGames.setItems(mpcp);
    }

    public void checkAnswer(String item,String id,int position) {

        Boolean answer;
        if(position==3){
            answer=true;
        }else answer=false;
        if(answer){
            iGames.correcAnswer(position);
        }
        else {
            iGames.wrongAnswer(position);
        }

    }
}
