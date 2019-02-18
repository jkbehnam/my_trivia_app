
package com.trivia.trivia.home.Events;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.eventList_unregistered;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_unregistered_event extends myFragment implements Ieventview{
    PrefManager pm;
    FragmentActivity c;
    eventList_unregistered madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    FragmentEventPresenter fragmentEventPresenter;
    ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_registered_events, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        c = getActivity();
        this.setFragmentActivity(c);
        setFragmentActivity(getActivity());
        fragmentEventPresenter = new FragmentEventPresenter(this);
        pm=new PrefManager(c);
        fragmentEventPresenter.refreshEventList(pm.getUserDetails().get("u_id"));
        return rootView;

    }

    public void setItems(ArrayList<Event> eventsList) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new eventList_unregistered(eventsList);
        rcle.setAdapter(madapter);
        madapter.setOnRegBtnClickListner(new eventList_unregistered.OnRegBtnClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                fragmentEventPresenter.reg_game(pm.getUserDetails().get("u_id"),eventsList.get(position).getE_id());
            }
        });

    }


    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
      hideLoading_base();
    }

}