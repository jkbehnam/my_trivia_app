package com.trivia.trivia.activities.Events;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_game_event_notstart;
import com.trivia.trivia.activities.Game.Fragment_gathering_event_notstart;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class FragmentEventPresenter {
    Ieventview fragment_event;
    String u_id;
    String g_id;
    static public FragmentRegisteredEvent fragment_registered_event = null;

    FragmentEventPresenter(Ieventview e, String u_id) {
        this.fragment_event = e;
        this.u_id = u_id;
    }

    public void refreshEventList(int page) {

        connectToServer.getEventsList(this, u_id, page);
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
        Fragment_main_event .group=groups;
        check_start_time(groups, e);


    }

    public void check_start_time(Group groups, Event e) {

        if (groups.getG_start_time().equals("0") || groups.getG_start_time().isEmpty() || groups.getG_start_time() == null) {

            fragment_event.start_non_concurrent_game_message(e, groups);
        } else if (groups.getG_end_time().equals("0") || groups.getG_end_time().isEmpty() || groups.getG_end_time() == null) {
            loadStartedFragment(e, groups);
        } else {
            dialog_event_end();
        }
    }

    public void dialog_event_end() {
        AlertDialog ad;
        DialogEventEnd dt = new DialogEventEnd();
        ad = dt.init(fragment_registered_event.getActivity(), "رویداد به پایان رسیده است.", "");

        ad.show();
    }

    public void start_game_timer(Event e, Group group) {
        connectToServer.start_game_timer(this, group, e);
        fragment_event.showLoading();
    }

    public void reciveRequeset_start_gamer_timer(String response, Group g, Event e) {
        if (response.equals("ok")) {
          //  loadStartedFragment(e, g);
            selectEvent(e,u_id);
            fragment_event.hideLoading();
        }

    }

    public void reciveRequeset(String response, int page) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Event> events2 = new ArrayList<>();
        try {
            final Event[] events = gson.fromJson(obj.getString("event"), Event[].class);

            events2.addAll(Arrays.asList(events));
        } catch (Exception e) {

        }

        fragment_event.setItems(events2, page);

    }

    public void selectEvent(Event e, String u_id) {

        Calendar c = Calendar.getInstance();
        if (e.getE_type().equals("مسابقه")) {

            if (Integer.parseInt(e.getE_start_time()) < c.getTimeInMillis() / 1000) {
                get_user_group(u_id, e);


            } else {
                loadGameNotStartedFragment(e);
            }
        } else if (e.getE_type().equals("گردهمایی")) {

            if (Integer.parseInt(e.getE_gather_start_time()) > c.getTimeInMillis() / 1000) {

                loadGatheringNotStartedFragment(e);
            } else {
                loadGatheringStartedFragment(e);
            }

        }
    }

    public void loadStartedFragment(Event e, Group g) {
        // load fragment
        //((BaseFragment) fragment_event)
        Fragment fragment = Fragment_main_event.newInstance(e, g);
        FragmentTransaction transaction = ((BaseFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadGameNotStartedFragment(Event e) {
        // load fragment
        //((BaseFragment) fragment_event)
        Fragment fragment = Fragment_game_event_notstart.newInstance(e);
        FragmentTransaction transaction = ((BaseFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadGatheringNotStartedFragment(Event e) {
        // load fragment
        //((BaseFragment) fragment_event)
        Fragment fragment = Fragment_gathering_event_notstart.newInstance(e,"",false);
        FragmentTransaction transaction = ((BaseFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadGatheringStartedFragment(Event e) {
        // load fragment
        //((BaseFragment) fragment_event)
        Fragment fragment = Fragment_gathering_event_notstart.newInstance(e,"",true);
        FragmentTransaction transaction = ((BaseFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void gotopayment(String u_id, String e_id) {
        fragment_event.payment(u_id, e_id);
    }

public static String getDateDifference(Long a,Long b){
    String ret="";
    try {


        //in milliseconds
        long diff = b - a;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");

         ret=diffDays+"روز و"+diffHours+"ساعت";

    } catch (Exception e) {
        e.printStackTrace();
    }
    return ret;
}
}
