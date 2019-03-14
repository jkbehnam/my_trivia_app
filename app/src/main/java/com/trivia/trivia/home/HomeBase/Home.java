package com.trivia.trivia.home.HomeBase;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.crashlytics.android.Crashlytics;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_main_ViewPager;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.login.LoginActivity;
import com.trivia.trivia.my_custom_widgets.my_AHBottomNavigation;

import java.util.Locale;

import co.ronash.pushe.Pushe;
import io.fabric.sdk.android.Fabric;

public class Home extends BaseActivity2 {

    my_AHBottomNavigation bottomNavigation;
    public static Context homecontext;
    public AHBottomNavigationViewPager viewPager;
    adapter_main_ViewPager adapter;

  //  DatabaseReference mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        homecontext = this;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bottonNav));

        }
        PrefManager pm = new PrefManager(this);
        if (pm.getUserDetails().get("name") == null) {

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            this.finish();
        }

        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.viewPagerMain);
//--------------------------------------------------------------
        bottomNavigation = (my_AHBottomNavigation) findViewById(R.id.bottom_navigation);

        viewPager.setOffscreenPageLimit(3);
        adapter = new adapter_main_ViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        bottomNavigation.setCurrentItem(0);
        viewPager.setCurrentItem(0, true);
      //  mydb = FirebaseDatabase.getInstance().getReference("message");
        bottomNavigation.setNotification(2, 1);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (viewPager.getCurrentItem() == position) {
                    //  adapter.set_clear();
                    // viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(position, true);
                } else
                    viewPager.setCurrentItem(position, true);
            //  mydb.setValue("hello");
                return true;
            }
        });


        Pushe.initialize(this, true);



    }

    @Override
    public int getLayout() {
        return R.layout.activity_home2;
    }

    @Override
    public void init() {

    }


}
