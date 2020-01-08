package com.trivia.trivia.activities.Game.GameAnswerQuestion.ShortAnswerAlert;

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

public class SAPresenter {
    IGamesSA iGames;
    Question question;
//connectToServerSA connectToServerSA;
connectToServerAns connectToServer;
    public SAPresenter(IGamesSA iGames2, Question question) {
        this.iGames = iGames2;
        this.question = question;
        this.connectToServer=new connectToServerAns();
       // this.connectToServerSA=new connectToServerSA(this);
    }

    public void getAnswerLenght() {
        iGames.showLoading();
    //    connectToServerSA.getShortAnswerLenght( question.getQ_id());

        Map<String, String> param = new HashMap<String, String>();
        param.put("q_id", question.getQ_id());

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                reciveSLenght(result);
            }
        }, param,  URLs.URL_GET_SHORT_ANSWER);

    }

    public void checkShortAnswer(String answer, String q_id, String id, String u_id) {
        iGames.showLoading();
       // connectToServerSA.answerShortAnswer( answer, id, q_id, u_id);

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        param.put("q_id", q_id);
        param.put("choose", answer);
        param.put("u_id", u_id);
        param.put("g_id", Fragment_main_event.group.getG_id() );
        param.put("e_id",Fragment_main_event.event.getE_id() );
        param.put("device", Settings.Secure.getString(homecontext.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                shortAnswerResponse(result, id);
            }
        }, param,   URLs.URL_ANSWER_MULTICHOICE);

    }
    public void shortAnswerResponse(String response, String id) {
        iGames.hideLoading();
        if (response.equals("true")) {
            iGames.showanimation();
            iGames.correcAnswer(1);
            iGames.finishFrag();
        } else {
            iGames.wrongAnswer(1);
          //  connectToServerSA.getQuestionScore( id);

            Map<String, String> param = new HashMap<String, String>();
            param.put("id", id);

            connectToServer.any_send(new VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {

                    setQuestionScore(result);
                }
            }, param,  URLs.URL_GET_QUESTION_SCORE);
        }
    }

    public void setQuestionScore(String score) {
        iGames.setscore(score);
    }


    public void reciveSLenght(String response) throws JSONException {
        iGames.hideLoading();
        iGames.setLenght(response);

    }
    public void getQuestionScore(String id) {
        //connectToServerSA.getQuestionScore( id);

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
            case 1:
                return "ساده";

            case 2:
                return "متوسط";

            case 3:
                return "دشوار";

            default:
                return "نامشخص";

        }

    }

}
