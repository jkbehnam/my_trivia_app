package com.trivia.trivia.ranking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.home.source.Fragment_source_list;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.util.obj_film;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentRankingPresenter {

    Fragment_ranking_list fragment_source_list;
    FragmentRankingPresenter(Fragment_ranking_list e) {
        fragment_source_list=e;
    }

    public void refreshEventList() {
        connectToServer.getRankingList(this);
        fragment_source_list.showLoading();

    }
    public void reciveRequeset(String response) throws JSONException {

        fragment_source_list.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<OtherGamer> questions2 = new ArrayList<>();
        final OtherGamer[] questions = gson.fromJson(obj.getString("rank"), OtherGamer[].class);
OtherGamer g=new OtherGamer();
g.setName("نام کاربری");
        g.setScore("امتیاز");
        questions2.add(g);
        for (int i = 0; i < questions.length; i++) {

            questions2.add(questions[i]);

        }
        fragment_source_list.setItems(questions2);

    }




}