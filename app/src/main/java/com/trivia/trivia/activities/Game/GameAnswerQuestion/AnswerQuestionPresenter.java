package com.trivia.trivia.activities.Game.GameAnswerQuestion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.util.MultipleChoiceItems;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class AnswerQuestionPresenter {
    IGames iGames;
    Question question;
    ArrayList<MultipleChoiceItems> events2;
    public AnswerQuestionPresenter(IGames iGames2, Question question) {
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

    //=====================
//test

    //==============


    events2.addAll(Arrays.asList(events));
    setItems(events2);
}
public void getMultipleChoice(){
        iGames.showLoading();
    connectToServer.getMultiplechoiceQuestion(this,question.getQ_id());

}
    public void getAnswerLenght(){
        iGames.showLoading();
        connectToServer.getShortAnswerLenght(this,question.getQ_id());

    }
    public void setItems(ArrayList<MultipleChoiceItems> mci) {

        MultipleChoicePage mpcp = new MultipleChoicePage(question.getQ_title(),"","","","","","");
        mpcp.setMpi(mci);
        iGames.setItems(mpcp);
    }

    public void checkAnswer(String title,String q_id,String id,int position,String u_id) {
        iGames.showLoading();
       connectToServer. answerMultichoice(this,title,id,q_id,u_id,position);

    }
    public void checkShortAnswer(String answer,String q_id,String id,String u_id) {
        iGames.showLoading();
        connectToServer. answerShortAnswer(this,answer,id,q_id,u_id);

    }
    public void checkDescAnswer(String answer,String q_id,String id,String u_id) {
        iGames.showLoading();
        connectToServer. answerDescAnswer(this,answer,id,q_id,u_id);

    }

    public void answerResponse(String response,int position,String id){
        iGames.hideLoading();
        if (response.equals("true")){
            iGames.showanimation();
            iGames.correcAnswer(position);
            iGames.finishFrag();
        }else {iGames.wrongAnswer(position);
          connectToServer.getQuestionScore(this,id);
    }}
    public void shortAnswerResponse(String response,String id){
        iGames.hideLoading();
        if (response.equals("true")){
            iGames.showanimation();
            iGames.correcAnswer(1);
            iGames.finishFrag();
        }else {iGames.wrongAnswer(1);
            connectToServer.getQuestionScore(this,id);
        }}
    public void setQuestionScore(String score){
        iGames.setscore(score);
    }


    public void reciveSLenght(String response) throws JSONException {
        iGames.hideLoading();
        iGames.setLenght(response);

    }
}
