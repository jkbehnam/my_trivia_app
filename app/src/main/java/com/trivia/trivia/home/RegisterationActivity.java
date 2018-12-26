package com.trivia.trivia.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.hbb20.CountryCodePicker;
import com.trivia.trivia.R;

import com.trivia.trivia.base.BaseActivity2;
import com.shuhart.stepview.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterationActivity extends BaseActivity2 implements View.OnClickListener, IRegisterationView {
    StepView stepView;

    private PinView verifyCodeET;
    RegistrationPresenter registrationPresenter;
    //LinearLayout layout1, layout2, layout3;
    @BindView(R.id.activity_reg_et_phoneNumber)
    EditText phoneNum;
    @BindView(R.id.activity_Reg_ccp)
    CountryCodePicker ccp;
    @BindView(R.id.phonenumberText)
    TextView phonenumberText;
    @BindView(R.id.activity_reg_btn_sendCode)
    Button btnSendCode;
    @BindView(R.id.activity_reg_btn_sendPhone)
    Button btnSendPhone;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.pinView)
    PinView pinView;

    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();

        stepView.setStepsNumber(3);
        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);


        registrationPresenter = new RegistrationPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_registeration;
    }

    @Override
    public void init() {
        /*layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        */
        verifyCodeET = (PinView) findViewById(R.id.pinView);
        stepView = findViewById(R.id.step_view);
        btnSendPhone.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_reg_btn_sendPhone: {
                String phoneNumber = phoneNum.getText().toString();
                Toast.makeText(this, phoneNum.getText().toString(), Toast.LENGTH_SHORT).show();
                phonenumberText.setText(phoneNumber);

                registrationPresenter.validatePhoneNumber(phoneNumber);
                switch (registrationPresenter.validatePhoneNumber(phoneNumber)) {
                    case "editTextEmpty":
                        phoneNum.setError("Enter a Phone Number");
                        phoneNum.requestFocus();
                        break;
                    case "PhoneNumberNotValid":
                        phoneNum.setError("Please enter a valid phone");
                        phoneNum.requestFocus();
                        break;
                    case "ok": {
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);
                        } else {
                            stepView.done(true);
                        }

                        //send to server
                        //   registrationPresenter.sendPhoneNumber(ccp.getSelectedCountryCodeWithPlus() + phoneNumber);
                    }
                    break;
                }
            }
            break;
            case R.id.activity_login_tv_register:

                break;

            case R.id.activity_reg_btn_sendCode:

                registrationPresenter.sendOptCode(pinView.getText().toString());

            /*    if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                */
                break;


        }
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void startTimer() {

    }

    @Override
    public void EnterCodePage() {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);

    }

    @Override
    public void EnterphonePage() {

    }

    @Override
    public void showEndTimerDialog() {

    }

    @Override
    public void numberExisterror() {

    }
}
