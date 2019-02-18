package com.trivia.trivia.my_custom_widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.trivia.trivia.R;


/**
 * Created by behnam on 4/18/2018.
 */

public class my_AHBottomNavigation extends AHBottomNavigation {
    public my_AHBottomNavigation(Context context) {
        super(context);
        this.createNavItems();
    }

    public my_AHBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.createNavItems();
    }

    public my_AHBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.createNavItems();
    }
    private void createNavItems() {

        //CREATE ITEMS
        AHBottomNavigationItem infoItem = new AHBottomNavigationItem(R.string.events, R.drawable.ic_dashboard_black_24dp, R.color.colorBottomNavigationInactive);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(R.string.messgaes, R.drawable.ic_notifications_black_24dp, R.color.colorBottomNavigationAccent);
        AHBottomNavigationItem homeItem = new AHBottomNavigationItem(R.string.pofile, R.drawable.ic_person_black_24dp, R.color.colorBottomNavigationAccent);


        //ADD THEM to bar
        this.addItem(infoItem);

        this.addItem(profileItem);

        this.addItem(homeItem);

//this.setAccentColor(R.color.colorBottomNavigationAccent);
        //set properties


        this.setAccentColor(getContext().getResources().getColor(R.color.colorPrimary));
        this.setInactiveColor(Color.parseColor("#747474"));
        //set current item

        this.setForceTint(true);
        this.setTranslucentNavigationEnabled(false);
        this.setTitleState(TitleState.ALWAYS_SHOW);

        //this.setNotification("1", 3);



        this.setBehaviorTranslationEnabled(false);
        this.setBackgroundColor(Color.parseColor("#f5f5f5"));
        this.setDefaultBackgroundColor(Color.parseColor("#f5f5f5"));

    }

}
