
package com.trivia.trivia.home.Events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trivia.trivia.adapter.*;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;


public class FragmentEvent extends myFragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)rootView. findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(R.color.colorAccent);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setFragmentActivity(getActivity());
        return rootView;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_registered_event(), "همه رویداد ها");
        adapter.addFragment(new Fragment_registered_event(), "رویداد های ثبتنام شده");
        viewPager.setAdapter(adapter);

    }

}