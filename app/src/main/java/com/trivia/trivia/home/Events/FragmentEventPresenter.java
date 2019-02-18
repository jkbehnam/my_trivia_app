package com.trivia.trivia.home.Events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.home.gameActivity.Fragment_event_notstart;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentEventPresenter {
    Ieventview fragment_event;

    FragmentEventPresenter(Ieventview e) {
        this.fragment_event = e;
    }

    public void refreshEventList(String id) {
        connectToServer.getEventsList(this,id);
        fragment_event.showLoading();
    }
    public void refreshEventList_registerd(String id) {
        connectToServer.getEventsList_reg(this,id);
        fragment_event.showLoading();
    }
    public void reciveRequeset(String response) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Event> events2 = new ArrayList<>();
        final Event[] events = gson.fromJson(obj.getString("event"), Event[].class);

        for (int i = 0; i < events.length; i++) {

            events2.add(events[i]);

        }
        fragment_event.setItems(events2);

    }

    public void selectEvent(Event e){
        Calendar c=Calendar.getInstance();
        if(Integer.parseInt(e.getE_start_time())<c.getTimeInMillis()/1000){
        loadStartedFragment(e);}
        else {loadNotStartedFragment(e);}
    }
    private void loadStartedFragment(Event e) {
        // load fragment
        //((myFragment) fragment_event)
        Fragment fragment=Fragment_main_event.newInstance(e);
        FragmentTransaction transaction = ((myFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadNotStartedFragment(Event e) {
        // load fragment
        //((myFragment) fragment_event)
        Fragment fragment= Fragment_event_notstart.newInstance(e);
        FragmentTransaction transaction = ((myFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
void reg_game(String u_id,String e_id){
connectToServer.regUserEvent(this,u_id,e_id);
}

}
