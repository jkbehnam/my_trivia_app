package com.trivia.trivia.activities.Game.GameSolveQuestion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolvingListFragmentPresenter {
    solvingListFragment solvingListFragment;
    public SolvingListFragmentPresenter(solvingListFragment solvingListFragment){
        this.solvingListFragment=solvingListFragment;
    }

    public void refreshEventList_wirhoutserver(List<Integer> selectedItemPositions) {
        solvingListFragment. refresh( selectedItemPositions);
    }
    public void refreshEventList() {
        connectToServer.getSolvedQuestionsList(this);
        solvingListFragment.showLoading();
    }
    public void reciveRequeset(String response) throws JSONException {
        solvingListFragment.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Question> questions2 = new ArrayList<>();
        final Question[] questions = gson.fromJson(obj.getString("questions"), Question[].class);

        questions2.addAll(Arrays.asList(questions));
        solvingListFragment.initComponent(questions2);

    }
    public void solvedQuestion(ArrayList<String> arrayList, List<Integer> selectedItemPositions){


            JSONArray jArrayActionLog = new JSONArray();
            //Log.e(LOG, selectQuery);
            //  ArrayList<Gamer> g = DataBase_read.give_gamers_list(context);

            for (String s : arrayList) {
                try {
                    JSONObject jObjectPatientQuestion = new JSONObject();
                    jObjectPatientQuestion.put("id", s);

                    jArrayActionLog.put(jObjectPatientQuestion);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        connectToServer.setSolvedQuestion(this,jArrayActionLog,selectedItemPositions);
        }



}
