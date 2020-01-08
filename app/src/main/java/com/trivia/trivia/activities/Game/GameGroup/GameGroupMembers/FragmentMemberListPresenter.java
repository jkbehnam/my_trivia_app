package com.trivia.trivia.activities.Game.GameGroup.GameGroupMembers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Game.GameRanking.Fragment_ranking_list;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.util.Member;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.util.URLs.URL_GET_MEMBER_DATE;

public class FragmentMemberListPresenter {

    Fragment_members_list fragment_ranking_list;

    FragmentMemberListPresenter(Fragment_members_list e) {
        fragment_ranking_list = e;
    }

    public void refreshEventList() {

        Map<String, String> param = new HashMap<String, String>();
        param.put("g_id", Fragment_main_event.group.getG_id());
        param.put("e_id", Fragment_main_event.event.getE_id());

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                reciveRequeset(result);

            }
        }, param, URL_GET_MEMBER_DATE);

    }

    public void reciveRequeset(String response) throws JSONException {

            fragment_ranking_list.hideLoading();
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
            JSONObject obj = new JSONObject(response);
            ArrayList<Member> questions2 = new ArrayList<>();

            final Member[] questions = gson.fromJson(obj.getString("members"), Member[].class);

            for (int i = 0; i < questions.length; i++) {
                questions2.add(questions[i]);

            }


            fragment_ranking_list.setItems(questions2);


    }


}
