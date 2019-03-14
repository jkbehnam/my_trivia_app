
package com.trivia.trivia.home.Events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.trivia.trivia.adapter.*;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Image;
import com.trivia.trivia.util.Tools;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentEvent extends myFragment {
    View rootView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
static  FragmentEvent fragmentEvent;
@BindView(R.id.mainImage)
ImageView iv_main;
@BindView(R.id.main_title)
TextView tv_main_title;
@BindView(R.id.main_subtitle)
TextView tv_main_subtitle;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, rootView);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        setFragmentActivity(getActivity());
       setToolbar(rootView,"رویداد ها");

        tabLayout.setupWithViewPager(viewPager);


        setupViewPager(viewPager);
        setCustomFont();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        initToolbar();
        connectToServer.getMainSetting(this);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_unregistered_event(), "همه رویداد ها");
        adapter.addFragment(new Fragment_registered_event(), "رویداد های ثبت نام شده");
        viewPager.setAdapter(adapter);

    }

    public static FragmentEvent newInstance() {
        if (fragmentEvent == null) {
            synchronized(FragmentEvent.class) {
                if (fragmentEvent == null) {
                    fragmentEvent = new FragmentEvent();
                }
            }
        }
        return fragmentEvent;
    }

    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/iran_sans.ttf"));
                }
            }
        }
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar)rootView. findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("رویداد ها");

    }

    public void set_main_img(String response) throws JSONException {
        JSONObject obj = new JSONObject(response);
        JSONObject userJson = obj.getJSONObject("setting");
        tv_main_title.setText(userJson.getString("title"));
        tv_main_subtitle.setText(userJson.getString("subtitle"));
        Glide.with(getActivity()).load(userJson.getString("url")).into(iv_main);
    }

}