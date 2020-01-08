package com.trivia.trivia.activities.Game.GameBuyQuestion;

import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyQuestionPresenter {
    BuyQuestionFragment buyQuestionFragment;

    BuyQuestionPresenter(BuyQuestionFragment buyQuestionFragment) {
        this.buyQuestionFragment = buyQuestionFragment;
    }

    void getScore() {
        connectToServer.getAcountBalance(this, Fragment_main_event.group);
    }

    void buyQuestion(int level, int type) {

        String q_type = null;
        switch (type) {
            case 1:
                buyQuestionFragment.showLoading_base();
                q_type = "ShortAnswer";
                connectToServer.buyQuestion(this, Fragment_main_event.event, Fragment_main_event.group, q_type, String.valueOf(3 - level));

                break;
            case 2:
                buyQuestionFragment.showLoading_base();
                q_type = "MultipleChoice";
                connectToServer.buyQuestion(this, Fragment_main_event.event, Fragment_main_event.group, q_type, String.valueOf(3 - level));

                break;
            case 0:
                q_type = "DescAnswer";
                connectToServer.buyQuestion(this, Fragment_main_event.event, Fragment_main_event.group, q_type, String.valueOf(3 - level));
                break;
        }
    }

    public void reciveRequeset(String response) throws JSONException {
        buyQuestionFragment.hideLoading_base();

        JSONObject obj = new JSONObject(response);
        JSONObject userJson = obj.getJSONObject("user");

        //creating a new user object
        //userJson.getString("Name")
        userJson.getString("id");
        if (userJson.getString("id").isEmpty() || userJson.getString("id") .equals("null") ) {
            buyQuestionFragment.toast("موچودی حساب کافی نیست");
        } else {
            buyQuestionFragment.toast("سوال با موفقیت خریداری شد");
            buyQuestionFragment.setPlayerScore(userJson.getString("g_score"));
        }
    }

    public void setBalance(String response) throws JSONException {
        JSONObject obj = new JSONObject(response);
        JSONObject userJson = obj.getJSONObject("score");
        buyQuestionFragment.setPlayerScore(userJson.getString("g_score"));
        buyQuestionFragment.setinflation(userJson.getString("inflation"));
    }
}
