package com.trivia.trivia.adapter;





import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trivia.trivia.activities.Events.main_event_frame;
import com.trivia.trivia.activities.Messages.main_message_frame;
import com.trivia.trivia.activities.Profile.Fragment_profile;

import java.util.ArrayList;



/**
 * Created by Mohammad on 29/11/2017.
 */

public  class adapter_viewpager_main extends FragmentStatePagerAdapter {
    main_event_frame FragmentEvent = new main_event_frame();
    main_message_frame fragmentGroupMessage=new main_message_frame();
    Fragment_profile fragment_registered_event = new Fragment_profile();


   static adapter_viewpager_main instans;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    public ArrayList<Fragment> fragments_main = new ArrayList<>();
    public adapter_viewpager_main(FragmentManager fm) {
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


    public static adapter_viewpager_main getInstance(FragmentManager fm)
    {
        if (instans==null)
            instans = new adapter_viewpager_main(fm);
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