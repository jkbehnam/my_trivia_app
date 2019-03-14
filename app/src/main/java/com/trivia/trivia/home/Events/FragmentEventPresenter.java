package com.trivia.trivia.home.Events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.Fragment_event_notstart;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.login.LoginActivity;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.util.Utils;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentEventPresenter {
    Ieventview fragment_event;
String u_id;
String g_id;
static public Fragment_registered_event fragment_registered_event=null;
    FragmentEventPresenter(Ieventview e,String u_id) {
        this.fragment_event = e;
        this.u_id=u_id;
    }

    public void refreshEventList() {

        connectToServer.getEventsList(this, u_id);
        fragment_event.showLoading();
    }

    public void refreshEventList_registerd() {

        connectToServer.getEventsList_reg(this, u_id);
        fragment_event.showLoading();
    }

    public void get_user_group(String u_id, Event e) {

        connectToServer.getuser_group(this, u_id, e);
        fragment_event.showLoading();
    }

    public void reciveGroupRequeset(String response, Event e) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);

        final Group groups = gson.fromJson(obj.getString("group"), Group.class);

        if (e.getE_start_type().equals("not_concurrent")){
        check_start_time(groups, e);}else {

            loadStartedFragment(e,groups);
        }

    }

    public void check_start_time(Group groups, Event e) {
        if (groups.getG_start_time().equals("0") || groups.getG_start_time().isEmpty() || groups.getG_start_time() == null) {

            fragment_event.start_non_concurrent_game_message(e,groups);
        }
        else {
            loadStartedFragment(e,groups);
        }
    }

    public void start_game_timer(Event e,Group group) {
        connectToServer.start_game_timer(this,group,e);
        fragment_event.showLoading();
    }
    public void reciveRequeset_start_gamer_timer(String response,Group g,Event e){
        if(response.equals("ok")){
            loadStartedFragment(e,g);
            fragment_event.hideLoading();
        }

    }

    public void reciveRequeset(String response) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Event> events2 = new ArrayList<>();
        try {
            final Event[] events = gson.fromJson(obj.getString("event"), Event[].class);

            for (int i = 0; i < events.length; i++) {

                events2.add(events[i]);

            }
        }catch (Exception e){

        }

        fragment_event.setItems(events2);

    }

    public void selectEvent(Event e, String u_id) {
        if (e.getE_start_type().equals("not_concurrent") && e.getE_start_time().equals("0")) {
            get_user_group(u_id, e);
        } else {
            Calendar c = Calendar.getInstance();
            if (Integer.parseInt(e.getE_start_time()) < c.getTimeInMillis() / 1000) {
                get_user_group(u_id, e);

            } else {
                loadNotStartedFragment(e);
            }
        }
    }

    public void loadStartedFragment(Event e,Group g) {
        // load fragment
        //((myFragment) fragment_event)
        Fragment fragment = Fragment_main_event.newInstance(e,g);
        FragmentTransaction transaction = ((myFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadNotStartedFragment(Event e) {
        // load fragment
        //((myFragment) fragment_event)
        Fragment fragment = Fragment_event_notstart.newInstance(e);
        FragmentTransaction transaction = ((myFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void reg_game(String u_id, Event e) {

        // fragment_event.toast(e.getE_goup_members()+" عضو نیاز است.");
        connectToServer.regUserEvent(this, u_id, e.getE_id());
    }
   public void gotopayment(String u_id,String e_id){
fragment_event.payment(u_id,e_id);
   }



}
