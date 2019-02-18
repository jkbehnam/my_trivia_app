
package com.trivia.trivia.home.Events;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.trivia.trivia.adapter.*;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.util.Event;


public class FragmentEvent extends myFragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
static  FragmentEvent fragmentEvent;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        setFragmentActivity(getActivity());


       setToolbar(rootView,"رویداد ها");

        tabLayout.setupWithViewPager(viewPager);


        setupViewPager(viewPager);
        setCustomFont();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_unregistered_event(), "همه رویداد ها");
        adapter.addFragment(new Fragment_registered_event(), "رویداد های ثبتنام شده");
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
}