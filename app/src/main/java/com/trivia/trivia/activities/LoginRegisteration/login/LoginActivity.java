package com.trivia.trivia.activities.LoginRegisteration.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.textfield.TextInputEditText;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Events.DialogPassword;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.HomeBase.Home;
import com.trivia.trivia.activities.LoginRegisteration.Registration.RegisterationActivity;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.adapter.SpinnerAdapter;
import com.trivia.trivia.base.BaseActivity;
import com.trivia.trivia.helper.HashedPassword;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.App;
import com.trivia.trivia.util.Constant;
import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.util.Languages;
import com.trivia.trivia.util.Utils;
import com.trivia.trivia.webservice.connectToServer;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static com.trivia.trivia.activities.LoginRegisteration.login.LoginPresenterImpl.faToEn;
import static com.trivia.trivia.util.URLs.URL_CHANGE_PASS;
import static com.trivia.trivia.util.URLs.URL_GET_MEMBER_DATE;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
    public static Context maincontext;

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    TextView forgetpass;
    private Spinner spinner;
    TextView tv_title;
    private LoginPresenterImpl mPresenter;
    List<Languages> langList;
    int check = 0;
    // @BindView(R.id.activity_login_tv_change_lang) TextView tvChangeLang;

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("jkbehnam@gmail.com");
        Crashlytics.setUserName("Test User");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        maincontext = this;
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        init();

        // TODO: Move this to where you establish a user session
        logUser();

        String lng = App.localeManager.getLanguage();
        for (Languages l : langList
        ) {
            if (l.getCode().equals(lng)) {
                spinner.setSelection(langList.indexOf(l));
                break;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++check > 1) {
                    // setNewLocale(langList.get(i).getCode(), true);
                    Toast.makeText(LoginActivity.this, "زبان های دریگر برنامه به زودی ارائه می شوند.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Toast.makeText(this, App.localeManager.getLanguage(), Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.mainBackground));
        }

        SpannableStringBuilder str = new SpannableStringBuilder("شهر ریاضی");
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_title.setText(str);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {

        etEmail = findViewById(R.id.activity_login_et_username);
        etPassword = findViewById(R.id.activity_login_et_password);
        forgetpass = (TextView) findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(this);
        etPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (etEmail != null && etPassword != null) {
                        if (Utils.isNetworkAvailable(LoginActivity.this)) {
                            mPresenter.callLogin(etEmail.getText().toString(), etPassword.getText().toString());
                        } else {
                            Utils.displayCommonAlertDialog(LoginActivity.this, "اتصال به اینترنت برقرار نمیباشد");
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        Button btnLogin = findViewById(R.id.alert_btn_ok);
        ProgressDialog mProgressDialog = new ProgressDialog(LoginActivity.this);
        btnLogin.setOnClickListener(this);
        mPresenter = new LoginPresenterImpl(this, new LoginInteractorImpl());
        TextView tvRegister = findViewById(R.id.activity_login_tv_register);
        tvRegister.setOnClickListener(this);
        spinner = findViewById(R.id.spinner);
        langList = Arrays.asList(new Languages("English", "fa", 11), new Languages("فارسی", "en", 11));
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, langList);
        spinner.setAdapter(spinnerAdapter);
        tv_title = findViewById(R.id.tvToolbarAllPage);
      //  setNewLocale("fa", true);

      //  App.localeManager.setNewLocale(this.getApplicationContext(), "fa");

    }

    @Override
    public void showLoading() {
        show_loading();
      /*  mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
        */
    }

    @Override
    public void hideLoading() {

        hide_Loading();
       /* if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
            */
    }

    @Override
    public void setEmailError(ErrorCode code) {
        if (etEmail != null) {
            if (code.getId() == ErrorCode.ENTER_EMAIL.getId()) {
                etEmail.setError(getResources().getString(R.string.activity_login_enter_email));
            } else if (code.getId() == ErrorCode.EMAIL_INVALID.getId()) {
                etEmail.setError(getResources().getString(R.string.activity_login_email_invalid));
            }
        }
    }

    @Override
    public void setPasswordError(ErrorCode code) {
        if (etPassword != null) {
            if (code.getId() == ErrorCode.ENTER_PASSWORD.getId()) {
                etPassword.setError(getResources().getString(R.string.activity_login_enter_password));
            } else if (code.getId() == ErrorCode.PASSWORD_INVALID.getId()) {
                etPassword.setError(getResources().getString(R.string.activity_login_password_err));
            }
        }
    }

    @Override
    public void loginSuccess(ResponseLogin responseLogin) {

        PrefManager pm = new PrefManager(this);
        pm.createLogin(responseLogin.getUsername(), responseLogin.getPhone(), responseLogin.getU_id());
        Intent openHomeScreen = new Intent(LoginActivity.this, Home.class);
        openHomeScreen.putExtra(Constant.PASS_TO_HOME_MSG, "This is HOME");
        startActivity(openHomeScreen);
        this.finish();
    }

    @Override
    public void loginFailure(ErrorCode errorCode) {
        if (errorCode.getId() == 4) {
            Toast.makeText(this, getResources().getString(R.string.activity_login_fail_msg), Toast.LENGTH_LONG).show();
        } else if (1 == errorCode.getId()) {
            Toast.makeText(this, "نام کابری یا رمز عبور اشتباه است", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loginFailure(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.alert_btn_ok:

                if (etEmail != null && etPassword != null) {
                    if (Utils.isNetworkAvailable(LoginActivity.this)) {
                        mPresenter.callLogin(etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        Utils.displayCommonAlertDialog(LoginActivity.this, "اتصال به اینترنت برقرار نمی باشد");
                    }
                }
                break;
            case R.id.activity_login_tv_register:
                Intent openRegisterscreen = new Intent(LoginActivity.this, RegisterationActivity.class);
                startActivity(openRegisterscreen);
                break;

            case R.id.forgetpass:
                setNewPassword();
                break;
        }
    }
public void setNewPassword(){

    {

        AlertDialog ad;
        DialogPassword dt = new DialogPassword();
        ad = dt.init(this, "شماره موبایل خود را وارد کنید");


        EditText editt = (EditText) dt.getview().findViewById(R.id.editText2);
        editt.setHint("09151234567");
        ImageView okBtn = (ImageView) dt.getview().findViewById(R.id.alert_btn_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editt.getText().length()==11){
                String pass=getRandomString(6);
                Map<String, String> param = new HashMap<String, String>();
                param.put("mobile", editt.getText().toString());
                param.put("pass",pass);
                param.put("hashedpass",HashedPassword.create(pass, "dfdf").toString());

                connectToServer.any_send(new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {

                        if(result.equals("ok")){
                            Toast.makeText(LoginActivity.this, "رمز عبور جدید برای شماره ارسال شد", Toast.LENGTH_SHORT).show();
                            ad.dismiss();
                        }

                    }
                }, param, URL_CHANGE_PASS);
            }else {
                    Toast.makeText(LoginActivity.this, "شماره وارد شده معتبر نیست", Toast.LENGTH_SHORT).show();
                }
        }});
        ad.show();
    }

}

    private static String getRandomString( int sizeOfRandomString){
         String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);

        for(int i=0;i<sizeOfRandomString;++i){
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        }
        return sb.toString();
    }
    private boolean setNewLocale(String language, boolean restartProcess) {
        App.localeManager.setNewLocale(this.getApplicationContext(), language);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
            Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
