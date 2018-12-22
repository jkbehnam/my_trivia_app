package com.trivia.trivia.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.util.Constant;



public class HomeActivity extends BaseActivity2 {

    private TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvMsg.setText(getIntent().getStringExtra(Constant.PASS_TO_HOME_MSG));

    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void init() {

        tvMsg = (TextView) findViewById(R.id.activity_home_tv_msg);
    }
}
