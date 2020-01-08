package com.trivia.trivia.activities.Events;

import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.util.ArrayList;

public  interface IeventAlertview {

    void  showLoading();
    void hideLoading();
    void toast(String s);

    void payment(String u_id, String e_id);
    void showPaydialog();
}
