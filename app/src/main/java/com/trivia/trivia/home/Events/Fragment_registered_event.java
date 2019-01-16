
package com.trivia.trivia.home.Events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.eventList_registered;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.HomeBase.Home;
import com.trivia.trivia.util.Event;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_registered_event extends myFragment {

    FragmentActivity c;
    eventList_registered madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    FragmentEventPresenter fragmentEventPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_registered_events, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        c = getActivity();
        setFragmentActivity(getActivity());
        fragmentEventPresenter = new FragmentEventPresenter(this);
        fragmentEventPresenter.refreshEventList();

        return rootView;

    }

    public void setItems(ArrayList<Event> eventsList) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new eventList_registered(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        madapter.setOnCardClickListner(new eventList_registered.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                fragmentEventPresenter.selectEvent(eventsList.get(position));
            }
        });

    }


}