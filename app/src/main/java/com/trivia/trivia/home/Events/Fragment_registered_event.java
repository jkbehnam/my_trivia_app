
package com.trivia.trivia.home.Events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.eventList_registered;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_registered_event extends myFragment implements Ieventview {
    FragmentActivity c;
    eventList_registered madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    FragmentEventPresenter fragmentEventPresenter;
    String user_id;

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
        FragmentEventPresenter.fragment_registered_event=this;
        fragmentEventPresenter.refreshEventList_registerd();
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentEventPresenter.refreshEventList_registerd();
            }
        });
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

                fragmentEventPresenter.selectEvent(eventsList.get(position), user_id);
            }
        });


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
        String message;
        String btn_cancle = "بستن پنجره";
        String btn_confirm = "شروع بازی";
        message = "زمان شروع این بازی برای هر بازیکن متفاوت میباشد.";
        SweetAlertDialog se = new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        se.setTitleText("برای شروع بازی آماده اید؟");
        se.setContentText(message);
        se.setConfirmText(btn_confirm);
        se.setCancelText(btn_cancle);
        // se .setCustomImage(R.drawable.exercise_icon_plus_recyclerview);
        se.showCancelButton(true);
        se.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        se.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {

                fragmentEventPresenter.start_game_timer(e,g);
                sDialog.dismissWithAnimation();
            }
        });
        se.show();

    }

    @Override
    public void payment(String u_id, String e_id) {

    }

public void refreshlist(){fragmentEventPresenter.refreshEventList_registerd();}
}