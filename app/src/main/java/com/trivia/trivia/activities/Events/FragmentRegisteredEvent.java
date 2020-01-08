
package com.trivia.trivia.activities.Events;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_event_registered;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class FragmentRegisteredEvent extends BaseFragment implements Ieventview {
    FragmentActivity c;
    adapter_event_registered madapter;
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
        FragmentEventPresenter.fragment_registered_event = this;
        fragmentEventPresenter.refreshEventList_registerd();
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentEventPresenter.refreshEventList_registerd();
            }
        });
        return rootView;

    }

    public void setItems(ArrayList<Event> eventsList, int page) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_event_registered(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        rcle.setItemAnimator(new DefaultItemAnimator());
        madapter.setOnCardClickListner(new adapter_event_registered.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, Event event) {

                fragmentEventPresenter.selectEvent(event, user_id);
            }
        });
        if (madapter.getItemCount() == 0) {
            empty_lay.setVisibility(View.VISIBLE);
        } else {
            empty_lay.setVisibility(View.GONE);
        }

    }


    public void showLoading() {
      //  showLoading_base();
        srf.setRefreshing(true);
    }

    public void hideLoading() {
       // hideLoading_base();
        srf.setRefreshing(false);

    }

    @Override
    public void toast(String s) {

    }

    @Override
    public void start_non_concurrent_game_message(Event e, Group g) {
/*
        String message;
        String btn_cancle = "بستن پنجره";
        String btn_confirm = "شروع بازی";
        message = "بازی رو شروع میکنی؟";
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

                fragmentEventPresenter.start_game_timer(e, g);
                sDialog.dismissWithAnimation();
            }
        });
        se.show();
*/
        new AlertDialog.Builder(getActivity())
                .setMessage("برای شروع بازی آماده اید؟")
                .setCancelable(false)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {fragmentEventPresenter.start_game_timer(e, g);
                        // Perform Your Task Here--When Yes Is Pressed.
                        dialog.cancel();
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Perform Your Task Here--When No is pressed
                        dialog.cancel();
                    }
                }).show();

    }

    @Override
    public void payment(String u_id, String e_id) {

    }

    public void refreshlist() {
        fragmentEventPresenter.refreshEventList_registerd();
    }
}