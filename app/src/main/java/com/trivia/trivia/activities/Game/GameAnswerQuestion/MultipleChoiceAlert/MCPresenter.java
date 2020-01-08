package com.trivia.trivia.activities.Game.GameAnswerQuestion.MultipleChoiceAlert;

import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.connectToServerAns;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.util.MultipleChoiceItems;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.activities.HomeBase.Home.homecontext;

public class MCPresenter {
    IGamesMC iGames;
    Question question;
    ArrayList<MultipleChoiceItems> events2;
    connectToServerAns connectToServer;

    public MCPresenter(IGamesMC iGames2, Question question) {
        this.iGames = iGames2;
        this.question = question;
        this.connectToServer=new connectToServerAns();
    }

    public void reciveRequeset(String response) throws JSONException {
        iGames.hideLoading();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        JSONObject obj = new JSONObject(response);
        events2 = new ArrayList<>();
        final MultipleChoiceItems[] events = gson.fromJson(obj.getString("multiple"), MultipleChoiceItems[].class);
        events2.addAll(Arrays.asList(events));
        setItems(events2);
    }
    //دریافت گزینه ها
    public void getMultipleChoice() {
        iGames.showLoading();
        Map<String, String> param = new HashMap<String, String>();
        param.put("q_id", question.getQ_id());
        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                reciveRequeset(result);
            }
        }, param,  URLs.URL_GET_MULTIPLECHOICE);
    }
    // نمایش گزینه ها برای کاربر
    public void setItems(ArrayList<MultipleChoiceItems> mci) {
        MultipleChoicePage mpcp = new MultipleChoicePage(question.getQ_title(), "", "", "", "", "", "");
        mpcp.setMpi(mci);
        iGames.setItems(mpcp);
    }
    //درخواست بررسی قیمت
    public void checkAnswer(String title, String q_id, String id, int position, String u_id) {
        iGames.showLoading();

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        param.put("q_id", q_id);
        param.put("choose", title);
        param.put("u_id", u_id);
        param.put("g_id",Fragment_main_event.group.getG_id() );
        param.put("e_id",Fragment_main_event.event.getE_id() );
        param.put("device", Settings.Secure.getString(homecontext.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                answerResponse(result, position, id);
            }
        }, param,   URLs.URL_ANSWER_MULTICHOICE);
    }
    // بررسی پاسخ
    public void answerResponse(String response, int position, String id) {
        iGames.hideLoading();
        if (response.equals("true")) {
           // iGames.showanimation();
            iGames.correcAnswer(position);
            iGames.finishFrag();
        } else {
            iGames.wrongAnswer(position);
            Map<String, String> param = new HashMap<String, String>();
            param.put("id", id);

            connectToServer.any_send(new VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {

                    setQuestionScore(result);
                }
            }, param,    URLs.URL_GET_QUESTION_SCORE);
        }
    }
    public void getQuestionScore(String id) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                setQuestionScore(result);
            }
        }, param,   URLs.URL_GET_QUESTION_SCORE);
    }
    public void setQuestionScore(String score) {
        iGames.setscore(score);
    }

    public String returnLevel(String level) {
        int lev = Integer.parseInt(level);
        switch (lev) {
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
