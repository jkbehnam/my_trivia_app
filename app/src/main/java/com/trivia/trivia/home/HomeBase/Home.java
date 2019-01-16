package com.trivia.trivia.home.HomeBase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.Events.FragmentEvent;
import com.trivia.trivia.webservice.VolleySingleton;

import static com.trivia.trivia.login.LoginActivity.maincontext;

public class Home extends BaseActivity2 {

public static Context homecontext;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            myFragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new FragmentEvent();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new FragmentEvent();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    fragment = new FragmentEvent();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        homecontext=this;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home2;
    }

    @Override
    public void init() {

    }

    private void loadFragment(myFragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
