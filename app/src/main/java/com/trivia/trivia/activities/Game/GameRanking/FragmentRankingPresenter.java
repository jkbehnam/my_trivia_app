package com.trivia.trivia.activities.Game.GameRanking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentRankingPresenter {

    Fragment_ranking_list fragment_ranking_list;

    FragmentRankingPresenter(Fragment_ranking_list e) {
        fragment_ranking_list = e;
    }

    public void refreshEventList() {
        connectToServer.getRankingList(this);
        fragment_ranking_list.showLoading();

    }

    public void reciveRequeset(String response) throws JSONException {
        if(!response.equals("expired")) {
            int rank_pos = 0;
            fragment_ranking_list.hideLoading();
            final GsonBuilder builder = new GsonBuilder();

            final Gson gson = builder.create();
            // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
            JSONObject obj = new JSONObject(response);
            ArrayList<OtherGamer> questions2 = new ArrayList<>();
            final OtherGamer[] questions = gson.fromJson(obj.getString("rank"), OtherGamer[].class);

            for (int i = 0; i < questions.length; i++) {
                if (questions[i].getG_id().equals(Fragment_main_event.group.getG_id())) {
                    rank_pos = i;
                }
                questions2.add(questions[i]);

            }
            fragment_ranking_list.setItems(questions2, rank_pos);
        }
        else fragment_ranking_list.showToast(" از این لحطه نمیتواید رتبه بندی را مشاهده کنید");
    }


}
