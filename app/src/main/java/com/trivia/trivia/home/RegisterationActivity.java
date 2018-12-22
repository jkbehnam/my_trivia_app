package com.trivia.trivia.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chaos.view.PinView;
import com.trivia.trivia.R;

import com.trivia.trivia.base.BaseActivity2;
import com.shuhart.stepview.StepView;

public class RegisterationActivity extends BaseActivity2 {
    StepView stepView;
    private PinView verifyCodeET;
    LinearLayout layout1,layout2,layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        stepView.setStepsNumber(3);
        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);

    }

    @Override
    public int getLayout() {
         return R.layout.activity_registeration;
    }

    @Override
    public void init() {
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        verifyCodeET = (PinView) findViewById(R.id.pinView);
        stepView = findViewById(R.id.step_view);
    }
}
