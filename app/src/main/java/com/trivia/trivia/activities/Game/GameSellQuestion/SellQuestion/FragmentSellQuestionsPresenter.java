package com.trivia.trivia.activities.Game.GameSellQuestion.SellQuestion;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.GameDescAnswerFragment;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.MultipleChoiceAlert.MultipleChoiceAlert;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.ShortAnswerAlert.ShortAnswerAlert;
import com.trivia.trivia.activities.Game.GameGamerQuestion.Iquestionview;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.SellQuestion;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentSellQuestionsPresenter {
    ISellquestionview fragment_event;
Boolean IsAll;
    FragmentSellQuestionsPresenter(ISellquestionview e,Boolean IsAll) {
        this.fragment_event = e;
        this.IsAll=IsAll;
    }

    public void refreshEventList(int page) {
        connectToServer.getSellQuestionsList(this, page);
        fragment_event.showLoading();
    }
    public void refreshMyEventList(int page) {
        connectToServer.getMySellQuestionsList(this, page);
        fragment_event.showLoading();
    }


    public void removeItem(int i) {
        fragment_event.removeitem(i);
    }


    public void reciveRequeset(String response) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<SellQuestion> questions2 = new ArrayList<>();
        final SellQuestion[] questions = gson.fromJson(obj.getString("sellquestions"), SellQuestion[].class);
        questions2.addAll(Arrays.asList(questions));
        fragment_event.setItems(questions2);
    }

    public void selectQuestion(SellQuestion q, int position) {
        Fragment fragment = null;

        /*
        switch (q.getQ_type()) {

            case "MultipleChoice":

                MultipleChoiceAlert multipleChoiceAlert = new MultipleChoiceAlert();
                multipleChoiceAlert.init_dialog(((BaseFragment) fragment_event).getActivity(), q, this, position);
                multipleChoiceAlert.show();
                // fragment= GameMultipleChoiceFragment.newInstance(q);
                break;
            case "ShortAnswer":
                // fragment= GameShortAnswerFragment.newInstance(q);

                ShortAnswerAlert shortAnswerAlert = new ShortAnswerAlert();
                shortAnswerAlert.init_dialog(((BaseFragment) fragment_event).getActivity(), q, this, position);
                shortAnswerAlert.show();
                break;
            case "DescAnswer":


                fragment = GameDescAnswerFragment.newInstance(q);
                break;
        }
        */
    }

    public void exchangeQuestion(String id,int position){
        connectToServer.sellExchangeQuestion(this,id,position);
    }
    public void sellExchangeRequeset(String re,int position){
       removeItem(position);
        fragment_event.dimissSellAlert();
    }





}
