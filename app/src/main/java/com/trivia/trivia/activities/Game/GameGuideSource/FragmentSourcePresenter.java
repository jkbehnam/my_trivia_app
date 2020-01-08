package com.trivia.trivia.activities.Game.GameGuideSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.util.obj_film;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentSourcePresenter {

    Fragment_source_list fragment_source_list;
    FragmentSourcePresenter(Fragment_source_list e) {
        fragment_source_list=e;
    }

    public void refreshEventList() {
        connectToServer.getSourceList(this);
        fragment_source_list.showLoading();

    }
    public void reciveRequeset(String response) throws JSONException {

        fragment_source_list.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<obj_film> questions2 = new ArrayList<>();
        final obj_film[] questions = gson.fromJson(obj.getString("source"), obj_film[].class);

        questions2.addAll(Arrays.asList(questions));
        fragment_source_list.setItems(questions2);

    }




}
