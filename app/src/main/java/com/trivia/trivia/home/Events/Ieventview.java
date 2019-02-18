package com.trivia.trivia.home.Events;

import android.app.Fragment;

import com.trivia.trivia.util.Event;

import java.util.ArrayList;

public interface Ieventview {
    public void setItems(ArrayList<Event> eventsList);

    public void  showLoading();
    public void hideLoading();
}
