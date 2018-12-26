package com.trivia.trivia.home;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.webservice.VolleySingleton;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.trivia.trivia.login.LoginActivity.maincontext;

public class RegistrationPresenter {
    IRegisterationView mlIRegisterationView;
   public RegistrationPresenter(IRegisterationView IRV){
      this.mlIRegisterationView=IRV;
    }
    void sendPhoneNumber(String phone){
        connectToServer.sendSms(this,phone);
    }
    String validatePhoneNumber(String phone){
        String regEx = "^[0-9]{11}$";
        if (TextUtils.isEmpty(phone)) {
            //edit text empty error method
            return "editTextEmpty";
        } else if (phone.length() < 10&&!phone.matches(regEx)) {
            return "PhoneNumberNotValid";
            // phone number not valid
        } else {
            return "ok";
           //ok method
            //goto next step...manage layouts
            // ارسال کد به سمت سرور
        }
    }
    public void sendOptCode(String code){
       connectToServer.sendOtp(this,code);

    }
    public  void smsRequestRecived(String response){

    }
   public void optRequestRecived(String response){
        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(response);
            boolean error = responseObj.getBoolean("error");
            if (!error) {
                mlIRegisterationView.toastMessage("کد دریافت شد");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parsing json object response
        // response will be a json object

    }
}
