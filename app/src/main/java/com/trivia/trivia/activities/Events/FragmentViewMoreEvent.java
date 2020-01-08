
package com.trivia.trivia.activities.Events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_event_show_more;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentViewMoreEvent extends BaseFragment implements Ieventview {
    FragmentActivity c;
    adapter_event_show_more madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    FragmentEventPresenter fragmentEventPresenter;
    String user_id;
    @BindView(R.id.empty_lay)
    LinearLayout empty_lay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_registered_events, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        setFragmentActivity(getActivity());
        PrefManager pm = new PrefManager(c);
        user_id = pm.getUserDetails().get("u_id");
        fragmentEventPresenter = new FragmentEventPresenter(this, user_id);
        fragmentEventPresenter.refreshEventList(0);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentEventPresenter.refreshEventList(0);
            }
        });
        return rootView;

    }

    public void setItems(ArrayList<Event> eventsList, int page) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_event_show_more(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        rcle.setItemAnimator(new DefaultItemAnimator());
        madapter.setOnCardClickListner(new adapter_event_show_more.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view,Event event) {

                if (event.getE_type().equals("گردهمایی")) {
                    DialogEventGathering dialog = new DialogEventGathering(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), user_id);
                    dialog.show();
                } else {
                    DialogEventGame dialog = new DialogEventGame(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), user_id);
                    dialog.show();
                }
            }
        });
        if (madapter.getItemCount() == 0) {
            empty_lay.setVisibility(View.VISIBLE);
        } else {
            empty_lay.setVisibility(View.GONE);
        }

    }


    public void showLoading() {
        showLoading_base();
        srf.setRefreshing(true);
    }

    public void hideLoading() {
        hideLoading_base();
        srf.setRefreshing(false);

    }

    @Override
    public void toast(String s) {

    }

    @Override
    public void start_non_concurrent_game_message(Event e, Group g) {

    }

    @Override
    public void payment(String u_id, String e_id) {

    }


    public void refreshlist() {
        fragmentEventPresenter.refreshEventList_registerd();
    }
}