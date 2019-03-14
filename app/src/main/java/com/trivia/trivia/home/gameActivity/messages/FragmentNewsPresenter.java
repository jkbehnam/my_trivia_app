package com.trivia.trivia.home.gameActivity.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.util.NewsArticle;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentNewsPresenter {

    Fragment_system_message fragment_news_list;

    FragmentNewsPresenter(Fragment_system_message e) {
        fragment_news_list = e;
    }

    public void refreshEventList() {
        connectToServer.getnewsList(this);
        fragment_news_list.showLoading();

    }

    public void reciveRequeset(String response) throws JSONException {
        fragment_news_list.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<NewsArticle> questions2 = new ArrayList<>();
        final NewsArticle[] questions = gson.fromJson(obj.getString("news"), NewsArticle[].class);

        for (int i = 0; i < questions.length; i++) {

            questions2.add(questions[i]);

        }
        fragment_news_list.setItems(questions2);

    }


}
