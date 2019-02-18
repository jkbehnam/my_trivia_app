package com.trivia.trivia.home.gameActivity;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.MultipleChoiceItems;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MultipleChoicePresenter {
    IGames iGames;
    Question question;
    ArrayList<MultipleChoiceItems> events2;
    public MultipleChoicePresenter(IGames iGames2,Question question) {
        this.iGames = iGames2;
        this.question=question;

    }

public void reciveRequeset(String response) throws JSONException {
        iGames.hideLoading();
    final GsonBuilder builder = new GsonBuilder();

    final Gson gson = builder.create();
    // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
    JSONObject obj = new JSONObject(response);
    events2 = new ArrayList<>();
    final MultipleChoiceItems[] events = gson.fromJson(obj.getString("multiple"), MultipleChoiceItems[].class);

    for (int i = 0; i < events.length; i++) {

        events2.add(events[i]);

    }
    setItems(events2);
}
public void getMultipleChoice(){
        iGames.showLoading();
    connectToServer.getMultiplechoiceQuestion(this,question.getQ_id());

}
    public void setItems(ArrayList<MultipleChoiceItems> mci) {

        MultipleChoicePage mpcp = new MultipleChoicePage(question.getQ_title(),"","","","","");
        mpcp.setMpi(mci);
        iGames.setItems(mpcp);
    }

    public void checkAnswer(String item,String id,int position) {

        Boolean answer;
        if(events2.get(position).getCorrect()==1){
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
