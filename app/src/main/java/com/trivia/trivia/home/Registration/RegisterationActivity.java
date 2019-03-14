package com.trivia.trivia.home.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.trivia.trivia.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class RegisterationActivity extends BaseActivity2 implements View.OnClickListener, IRegisterationView {
    StepView stepView;
    private ProgressDialog mProgressDialog;
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
    @BindView(R.id.activity_register_et_username)
    EditText et_username;
    @BindView(R.id.activity_register_et_password)
    EditText et_password;
    @BindView(R.id.activity_register_et_password_rep)
    EditText et_password_rep;
    @BindView(R.id.activity_register_btn_username)
    Button btn_username;
    private int currentStep = 0;
    public String phoneNumTxt;
    int checktime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();

        stepView.setStepsNumber(3);
        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);
        this.setToolbar("ثبت نام");
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
        mProgressDialog = new ProgressDialog(RegisterationActivity.this);
        verifyCodeET = (PinView) findViewById(R.id.pinView);
        stepView = findViewById(R.id.step_view);
        btnSendPhone.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);
        btn_username.setOnClickListener(this);

        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
               if( et_username.getText().length() > 0){
               if (checktime++!=0){
                registrationPresenter.username_check(et_username.getText().toString());}
            }}
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_reg_btn_sendPhone: {
                String phoneNumber = phoneNum.getText().toString();
                Toast.makeText(this, phoneNum.getText().toString(), Toast.LENGTH_SHORT).show();
                phonenumberText.setText(phoneNumber);
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

                        phoneNumTxt = ccp.getSelectedCountryCodeWithPlus() + phoneNumber;
                        //send to server
                        registrationPresenter.sendPhoneNumber(phoneNumTxt);
                    }
                    break;
                }
            }
            break;
            case R.id.activity_login_tv_register:

                break;

            case R.id.activity_reg_btn_sendCode:

                registrationPresenter.sendOptCode(pinView.getText().toString(), phoneNumTxt);
                break;

            case R.id.activity_register_btn_username:

                registrationPresenter.setUserPass(et_username.getText().toString(), et_password.getText().toString(), et_password_rep.getText().toString(), phoneNumTxt);

                break;


        }
    }

    @Override
    public void toastSuccessMessage(String message) {

        Toasty.success(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void toastFailMessage(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
    }


    @Override
    public void gotoNextPage() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showLoading() {
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void startTimer() {

    }

    @Override
    public void EnterCodePage() {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.GONE);
    }

    @Override
    public void EnterphonePage() {

    }

    @Override
    public void optVerified() {


        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);
        stepView.go(2, true);
    }

    @Override
    public void showEndTimerDialog() {

    }

    @Override
    public void numberNotExist() {
        layout2.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        stepView.go(1, true);
    }

    public void uniqueUsername() {
        Drawable myIcon = getResources().getDrawable(R.drawable.ok);
        myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
        et_username.setError("unique", myIcon);

    }
    public void repetitiousUsername() {
        Drawable myIcon = getResources().getDrawable(R.drawable.cancel);
        myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
        et_username.setError("repetitious", myIcon);

    }

}
