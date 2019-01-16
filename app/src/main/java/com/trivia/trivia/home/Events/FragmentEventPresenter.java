package com.trivia.trivia.home.Events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.webservice.connectToServer;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentEventPresenter {
    Fragment_registered_event fragment_registered_event;

    FragmentEventPresenter(Fragment_registered_event e) {
        this.fragment_registered_event = e;
    }

    public void refreshEventList() {
        connectToServer.getEventsList(this);
        fragment_registered_event.showLoading();
    }

    public void reciveRequeset(String response) throws JSONException {
        fragment_registered_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        JSONArray us = new JSONArray(obj.getString("event"));
        ArrayList<Event> events2 = new ArrayList<>();
        final Event[] events = gson.fromJson(obj.getString("event"), Event[].class);

        for (int i = 0; i < events.length; i++) {

            events2.add(events[i]);

        }
        fragment_registered_event.setItems(events2);

    }

    public void selectEvent(Event e){
        loadFragment(e);
    }
    private void loadFragment(Event e) {
        // load fragment
        Fragment fragment=Fragment_main_event.newInstance(e);
        FragmentTransaction transaction = fragment_registered_event.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
