package com.trivia.trivia.home.gameActivity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity2;

public class GameMainActivity extends BaseActivity2

       {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = null;
        fragment =  MultipleChoiceFragment.newInstance("ddd","behnam");
        transaction.add(R.id.container_setting, fragment);
        transaction.commit();

    }

           @Override
           public int getLayout() {
               return R.layout.activity_game_main;
           }

           @Override
           public void init() {

           }

       }
