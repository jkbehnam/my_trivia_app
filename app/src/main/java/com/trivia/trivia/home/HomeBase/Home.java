package com.trivia.trivia.home.HomeBase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.crashlytics.android.Crashlytics;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_main_ViewPager;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.login.LoginActivity;
import com.trivia.trivia.my_custom_widgets.my_AHBottomNavigation;

import co.ronash.pushe.Pushe;
import io.fabric.sdk.android.Fabric;

public class Home extends BaseActivity2 {

    my_AHBottomNavigation bottomNavigation;
    public static Context homecontext;
    public AHBottomNavigationViewPager viewPager;
    adapter_main_ViewPager adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        PrefManager pm = new PrefManager(this);
        if (pm.getUserDetails().get("name") == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        viewPager = (AHBottomNavigationViewPager)findViewById(R.id.viewPagerMain);
//--------------------------------------------------------------
        bottomNavigation = (my_AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager.setOffscreenPageLimit(3);
        adapter = new adapter_main_ViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        bottomNavigation.setCurrentItem(0);
        viewPager.setCurrentItem(0, true);

        bottomNavigation.setNotification(2, 1);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (viewPager.getCurrentItem() == position) {
                    //  adapter.set_clear();
                   // viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(position, true);
                }else
                viewPager.setCurrentItem(position, true);
                return true;
            }
        });

        homecontext = this;
        Pushe.initialize(this,true);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home2;
    }

    @Override
    public void init() {

    }


}
