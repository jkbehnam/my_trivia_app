package com.trivia.trivia.home.Registration;

import android.text.TextUtils;

import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPresenter {
    IRegisterationView mlIRegisterationView;

    public RegistrationPresenter(IRegisterationView IRV) {
        this.mlIRegisterationView = IRV;
    }

    void sendPhoneNumber(String phone) {
        mlIRegisterationView.showLoading();
        connectToServer.sendSms(this, phone);
    }

    String validatePhoneNumber(String phone) {
        String regEx = "^[0-9]{11}$";
        if (TextUtils.isEmpty(phone)) {
            //edit text empty error method
            return "editTextEmpty";
        } else if (phone.length() < 10 && !phone.matches(regEx)) {
            return "PhoneNumberNotValid";
            // phone number not valid
        } else {
            return "ok";
            //ok method
            //goto next step...manage layouts
            // ارسال کد به سمت سرور
        }
    }

    public void sendOptCode(String code) {
        mlIRegisterationView.showLoading();
        connectToServer.sendOtp(this, code);

    }

    public void smsRequestRecived(String response) {
        mlIRegisterationView.hideLoading();
        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(response);
            boolean error = responseObj.getBoolean("error");
            if (!error) {
                mlIRegisterationView.toastMessage("کد ارسال شد");
                mlIRegisterationView.numberNotExist();
            }else if(responseObj.getString("message").equals("Mobile number already existed!")){
                mlIRegisterationView.toastMessage("شماره تکراری است");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void optRequestRecived(String response) {
        mlIRegisterationView.hideLoading();
        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(response);
            boolean error = responseObj.getBoolean("error");
            if (!error) {
                //mlIRegisterationView.toastMessage("کد دریافت شد");
                 if(responseObj.getString("message").equals("User created successfully!")){
                    mlIRegisterationView.optVerified();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parsing json object response
        // response will be a json object

    }

}
