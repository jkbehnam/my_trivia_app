package com.trivia.trivia.home.Events;

import android.app.Fragment;

import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.util.ArrayList;

public interface Ieventview {
    public void setItems(ArrayList<Event> eventsList);

    public void  showLoading();
    public void hideLoading();
    public void toast(String s);
    public void start_non_concurrent_game_message(Event e, Group g);
    public void payment(String u_id,String e_id);
}
