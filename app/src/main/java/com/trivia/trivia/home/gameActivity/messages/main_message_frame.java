package com.trivia.trivia.home.gameActivity.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;
import com.trivia.trivia.home.Events.FragmentEvent;


public class main_message_frame extends Fragment {

    public main_message_frame() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_home_frag, container, false);

        FragmentManager childFragmentManager = getChildFragmentManager();
        int backStackCounter = childFragmentManager.getBackStackEntryCount();

        /**
         * get child fragment manager back stack counter first
         */
        if (backStackCounter == 0) {
            /**
             * if it is zero, find if stack 1 fragment has already existed or not
             */
            Fragment_message stack1Fragment = (Fragment_message) childFragmentManager.findFragmentByTag("stack1");
            /**
             * if stack 1 fragment does not exist, create a new one
             * we don't need to do anything if it has already existed because Android OS will handle it
             */
            if (stack1Fragment == null) {
                stack1Fragment = Fragment_message.newInstance();
                /**
                 * not adding this transaction, so back button will NOT go back to the container
                 * in tab 1 fragment
                 */
                childFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, stack1Fragment, "stack1")
                        .commit();
            }
        }

        return v;
    }



}