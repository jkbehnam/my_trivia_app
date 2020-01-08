package com.trivia.trivia.activities.Events;

import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.util.ArrayList;

public interface Ieventview {
    void setItems(ArrayList<Event> eventsList, int page);

    void  showLoading();
    void hideLoading();
    void toast(String s);
    void start_non_concurrent_game_message(Event e, Group g);
    void payment(String u_id, String e_id);
}
