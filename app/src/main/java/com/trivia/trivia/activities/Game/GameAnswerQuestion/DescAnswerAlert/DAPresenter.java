package com.trivia.trivia.activities.Game.GameAnswerQuestion.DescAnswerAlert;

import android.content.Context;
import android.provider.Settings;

import com.trivia.trivia.activities.Game.Fragment_main_event;

import com.trivia.trivia.activities.Game.GameAnswerQuestion.connectToServerAns;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.URLs;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.activities.HomeBase.Home.homecontext;

public class DAPresenter {
    IGamesDA iGames;
    Question question;
    connectToServerAns connectToServer;
Context context;
    public DAPresenter(IGamesDA iGames2, Question question) {
        this.iGames = iGames2;
        this.question = question;
        this.context=context;
        this.connectToServer=new connectToServerAns();
    }




    public void checkDescAnswer(String answer, String q_id, String id, String u_id){
        iGames.showLoading();
       // connectToServerDa.answerDescAnswer( answer, id, q_id, u_id);

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        param.put("device", Settings.Secure.getString(homecontext.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        param.put("answer", answer);
        param.put("e_id",Fragment_main_event.event.getE_id() );

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                shortAnswerResponse(result,"");

            }
        }, param,  URLs.URL_ANSWER_DESC);

    }
    public void shortAnswerResponse(String response, String id) {
        iGames.hideLoading();
        if (response.equals("send")) {
            iGames.showanimation();
            iGames.correcAnswer(1);
            iGames.finishFrag();
        } else {
          //  iGames.wrongAnswer(1);
         //   connectToServerSA.getQuestionScore( id);
        }
    }

    public void setQuestionScore(String score) {
        iGames.setscore(score);
    }


    public void getQuestionScore(String id) {
       // connectToServerDa.getQuestionScore(id);

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                setQuestionScore(result);

            }
        }, param,   URLs.URL_GET_QUESTION_SCORE);
    }
    public String returnLevel(String level){
        int lev=Integer.parseInt(level);
        switch (lev){
            case 0:
                return "ساده";

            case 1:
                return "متوسط";

            case 2:
                return "دشوار";

            default:
                return "نامشخص";

        }

    }
}
