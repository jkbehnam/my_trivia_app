package com.trivia.trivia.adapter;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trivia.trivia.home.Events.FragmentEvent;
import com.trivia.trivia.home.Events.Fragment_registered_event;
import com.trivia.trivia.home.Events.main_event_frame;
import com.trivia.trivia.home.gameActivity.FragmentChat.FragmentGroupMessage;
import com.trivia.trivia.home.gameActivity.messages.Fragment_message;
import com.trivia.trivia.home.profile.Fragment_profile;

import java.util.ArrayList;



/**
 * Created by Mohammad on 29/11/2017.
 */

public  class adapter_main_ViewPager extends FragmentStatePagerAdapter {
    main_event_frame FragmentEvent = new main_event_frame();
    Fragment_message fragmentGroupMessage=new Fragment_message();
    Fragment_profile fragment_registered_event = new Fragment_profile();


   static adapter_main_ViewPager instans;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    public ArrayList<Fragment> fragments_main = new ArrayList<>();
    public adapter_main_ViewPager(FragmentManager fm) {
        super(fm);



        fragments.clear();
        fragments.add(FragmentEvent);
        fragments.add(fragmentGroupMessage);
        fragments.add(fragment_registered_event);


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

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}