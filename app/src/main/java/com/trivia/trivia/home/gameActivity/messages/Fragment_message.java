
package com.trivia.trivia.home.gameActivity.messages;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.trivia.trivia.R;
import com.trivia.trivia.adapter.ViewPagerAdapter;
import com.trivia.trivia.base.myFragment;

import java.util.ArrayList;


public class Fragment_message extends myFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPager pager;
    FragmentActivity c;
    static Fragment_message fh;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.exercise_fragment_message,container,false);
        setRetainInstance(true);
        c = getActivity();
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager2);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs2);
        fh=this;


        tabLayout.setupWithViewPager(viewPager);


      setToolbar(rootView,"خبر ها");

        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);
        setCustomFont();
        setCustomFont();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_system_message(), "خبر ها");
        adapter.addFragment(new Fragment_my_message(), "اطلاعیه من");
        viewPager.setAdapter(adapter);

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