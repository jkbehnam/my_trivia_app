
package com.trivia.trivia.home.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.github.mikephil.charting.data.BarEntry;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_gamers_list;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.my_custom_widgets.my_BarChart;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.OtherGamer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_profile extends myFragment {
    @BindView(R.id.iv_exit)
    ImageView iv_exit;
    @BindView(R.id.pb1)
    TextRoundCornerProgressBar pb;
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rv_rank;
    @BindView(R.id.chart1)
    my_BarChart chart_1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        setToolbar(rootView, "صفحه شخصی");
        pb.setProgress(66);
        pb.setProgressColor(Color.parseColor("#9370DB"));
        get_step_data();
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager pm = new PrefManager(getActivity());
                pm.clearSession();
                getActivity().finish();
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_rank.setLayoutManager(mLayoutManager);
        rv_rank.setNestedScrollingEnabled(false);
        show_list();
        return rootView;

    }


    public static Fragment_profile newInstance(Event event) {
        Fragment_profile fragment = new Fragment_profile();
        Bundle bundle = new Bundle();
        //  bundle.putSerializable(EVENT_KEY, event);
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

    public void get_step_data() {


        ArrayList<BarEntry> yVals;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();

        chart_1.setVisibility(View.VISIBLE);


        // ArrayList<Chart_data> chartData = DataBase_read.get_steps(c);
        //  Collections.reverse(chartData);

        xVals.add("97/11/2");
        yVals.add(new BarEntry(0, 50, " شما در تاریخ "));
        xVals.add("97/11/3");
        yVals.add(new BarEntry(1, 60, " شما در تاریخ "));
        xVals.add("97/11/4");
        yVals.add(new BarEntry(2, 40, " شما در تاریخ "));
        xVals.add("97/11/5");
        yVals.add(new BarEntry(3, 90, " شما در تاریخ "));
        xVals.add("97/11/6");
        yVals.add(new BarEntry(4, 15, " شما در تاریخ "));
        xVals.add("97/11/7");
        yVals.add(new BarEntry(5, 0, " شما در تاریخ "));
        xVals.add("97/11/8");
        yVals.add(new BarEntry(6, 50, " شما در تاریخ "));

        //----------------------------------------
        String s = "s";
        ArrayList<Integer> color = new ArrayList<Integer>();
        //--------------------------------------
        int i = 0;



/*
        chart_1.setDrawValueAboveBar(true);
        Bitmap starBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.medal);
        if (chartData.size() > 0) {
            chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
        }
        chart_1.setMarker(mv);

        */


        chart_1.chart_data(xVals, yVals, getActivity());
        chart_1.set_prefrence(getActivity().getApplicationContext());

    }

    public void show_list() {
        ArrayList<OtherGamer> dv_list = new ArrayList<>();
dv_list.add(new OtherGamer("behnam","099594","50","2"));
        dv_list.add(new OtherGamer("بهنام","099594","50","2"));
        dv_list.add(new OtherGamer("hamid","099594","50","2"));
        dv_list.add(new OtherGamer("حسین","099594","50","2"));
        dv_list.add(new OtherGamer("علی","099594","50","2"));
        dv_list.add(new OtherGamer("behzad","099594","50","2"));
        String p_code = "";
        adapter_gamers_list madapter;
        madapter = new adapter_gamers_list(dv_list, p_code);


        rv_rank.setAdapter(madapter);
    }
}