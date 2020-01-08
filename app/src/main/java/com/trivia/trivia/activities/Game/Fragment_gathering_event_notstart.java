
package com.trivia.trivia.activities.Game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Event;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;


public class Fragment_gathering_event_notstart extends BaseFragment {
    @BindView(R.id.cv_countdownViewTest2)
    CountdownView countDownTimer;
    @BindView(R.id.expandableLayout_group)
    ExpandableLinearLayout expandableLayout_group;
    @BindView(R.id.expandableLayout_detail)
    ExpandableLinearLayout expandableLayout_detail;
    @BindView(R.id.event_group_btn)
    Button group_btn;
    @BindView(R.id.event_detail_btn)
    Button detail_btn;
    @BindView(R.id.item_event_tv_main)
    TextView item_event_tv_main;

    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_info)
    TextView tv_info;
@BindView(R.id.tr_1)
    TableRow tr_1;
@BindView(R.id.tr_2)
TableRow tr_2;
@BindView(R.id.imageView14)
    ImageView close;
    @BindView(R.id.tv_event_type)
    TextView tv_event_type;
    Event event;
String title;
Boolean started;
Fragment fragment;
    private static final String EVENT_KEY = "event_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_event_notstart, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        fragment=this;
        tr_1.setVisibility(View.GONE);
        tr_2.setVisibility(View.GONE);
        event = (Event) getArguments().getSerializable(
                EVENT_KEY);

        title =  getArguments().getString(
                "title");
        started =  getArguments().getBoolean(
                "started");
        group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_group.toggle();
            }
        });
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_detail.toggle();
            }
        });
        item_event_tv_main.setText(event.getE_name());
if(!started){
        Calendar c = Calendar.getInstance();

        countDownTimer.start(((Integer.parseInt(event.getE_gather_start_time())) - (c.getTimeInMillis() / 1000)) * 1000);
        tv_event_type.setText("تا آغاز گردهمایی");
}else {
    countDownTimer.setVisibility(View.GONE);
    tv_event_type.setText("رویداد آغاز شده");
}
        tv_info.setText(event.getE_description());
        try {
            tv_location.setText(event.getE_address());
        } catch (Exception e) {
            tv_location.setVisibility(View.GONE);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
        return rootView;

    }


    public static Fragment_gathering_event_notstart newInstance(Event event,String title,Boolean started) {
        Fragment_gathering_event_notstart fragment = new Fragment_gathering_event_notstart();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, event);
        bundle.putString("title", title);
        bundle.putBoolean("started", started);
        fragment.setArguments(bundle);

        return fragment;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}