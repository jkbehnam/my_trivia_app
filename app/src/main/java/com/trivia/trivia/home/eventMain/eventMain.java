package com.trivia.trivia.home.eventMain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class eventMain extends Fragment {


    public eventMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_main, container, false);
    }

}
