
package com.trivia.trivia.activities.Profile;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.custom_widgets.my_BarChart;
import com.trivia.trivia.util.Event;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_profile extends BaseFragment implements View.OnClickListener {
    profile_presenter profile_presenter;
    @BindView(R.id.pb1)
    TextRoundCornerProgressBar pb;
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rv_rank;
    @BindView(R.id.chart1)
    my_BarChart chart_1;
    @BindView(R.id.iv_exit)
    ImageView iv_exit;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_score)
    TextView tv_score;

    @BindView(R.id.tx_pb1)
    TextView tx_pb1;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile_freelancer, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        profile_presenter = new profile_presenter(this);
        setToolbar(rootView, "صفحه شخصی");

        get_step_data();

        try {
            PrefManager pm = new PrefManager(getActivity());
            pm.getUserDetails().get("name");
            String Nickname = pm.getUserDetails().get("name");
            String u_id = pm.getUserDetails().get("u_id");
            tv_user_name.setText(Nickname);
            profile_presenter.get_score(u_id);
        } catch (Exception e) {
        }


        iv_setting.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_rank.setLayoutManager(mLayoutManager);
        rv_rank.setNestedScrollingEnabled(false);


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exit:
                PrefManager pm = new PrefManager(getActivity());
                pm.clearSession();
                getActivity().finish();
                break;
            case R.id.iv_setting:
                Fragment_setting fragment = new Fragment_setting();
                fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());
                break;
        }
    }

    public void showSettingBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.activity_setting_sectioned, null);
        view.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });


        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);


        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    public void set_score(String score) {
        tv_score.setText(score + " پروف");

    }

    public void set_ex_score(String score) {
        try {
            int f = Integer.parseInt(score);

            int x = highestPowerof2(Integer.parseInt(score));
            int z2 = x;
            for (int z = 0; ; z++) {

                if (Math.pow(2, z) >= x) {
                    tx_pb1.setText("سطح " + z);
                    break;
                }


            }


            int i = Integer.parseInt(score) - x;

            int i2 = x * 2 - x;
            Float prg = Float.valueOf(Float.valueOf(i) / Float.valueOf(i2) * 100);

            pb.setProgress(prg);
            pb.setProgressColor(Color.parseColor("#9370DB"));
        }catch (Exception e){}
    }

    static int highestPowerof2(int n) {
        int res = 0;
        for (int i = n; i >= 1; i--) {
            // If i is a power of 2
            if ((i & (i - 1)) == 0) {
                res = i;
                break;
            }
        }

        return res;
    }
}