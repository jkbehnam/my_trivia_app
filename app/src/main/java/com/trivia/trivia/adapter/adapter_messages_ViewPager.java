package com.trivia.trivia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


/**
 * Created by Mohammad on 29/11/2017.
 */

public  class adapter_messages_ViewPager extends FragmentStatePagerAdapter {
    String[] mTitles;
   static adapter_messages_ViewPager instans;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    public adapter_messages_ViewPager(FragmentManager fm, ArrayList<Fragment> frag, String[] mTitles) {
        super(fm);
this.mTitles=mTitles;
        fragments.clear();
        for (Fragment ft:frag
             ) {
            fragments.add(ft);
        }








    }
    public static adapter_messages_ViewPager getInstance(FragmentManager fm, ArrayList<Fragment> frag, String[] mTitles)
    {
        if (instans==null)
            instans = new adapter_messages_ViewPager(fm,frag,mTitles);
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}