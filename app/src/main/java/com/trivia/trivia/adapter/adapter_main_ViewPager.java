package com.trivia.trivia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.trivia.trivia.home.Events.FragmentEvent;

import java.util.ArrayList;


/**
 * Created by Mohammad on 29/11/2017.
 */

public  class adapter_main_ViewPager extends FragmentStatePagerAdapter {
    FragmentEvent FragmentEvent = new FragmentEvent();



   static adapter_main_ViewPager instans;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    public ArrayList<Fragment> fragments_main = new ArrayList<>();
    public adapter_main_ViewPager(FragmentManager fm) {
        super(fm);



        fragments.clear();
        fragments.add(FragmentEvent);
        fragments.add(FragmentEvent);
        fragments.add(FragmentEvent);
        fragments.add(FragmentEvent);
        fragments.add(FragmentEvent);
/*
        fragments_main.clear();
        fragments_main.add(infoFragment);
        fragments_main.add(chartsFragment);
        fragments_main.add(homeFragment);
        fragments_main.add(messageFragment);
        fragments_main.add(progileFragment);

*/

    }
    public static adapter_main_ViewPager getInstance(FragmentManager fm)
    {
        if (instans==null)
            instans = new adapter_main_ViewPager(fm);
        return instans;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



}