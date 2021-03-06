package com.trivia.trivia.activities.LoginRegisteration.login;

import android.provider.Settings;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.helper.HashedPassword;
import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.webservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.activities.LoginRegisteration.login.LoginActivity.maincontext;

/**
 * Created by cstudioo on 06/01/17.
 */

public class LoginInteractorImpl implements ILoginInteractor {



    @Override
    public void login(String userName, String passWord, IValidationErrorListener validationErrorListener, final IOnLoginFinishedListener loginFinishedListener) {
        if (isDataValid(userName, passWord, validationErrorListener)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new com.android.volley.Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                loginFinishedListener.getUserData(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          //  Toast.makeText(getApplicationContext(), "خطا در اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                            loginFinishedListener.errorMsg(error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("Username", faToEn(userName));
                    params.put("Password", HashedPassword.create(faToEn(passWord),"dfdf").toString());
                    params.put("device", Settings.Secure.getString(maincontext.getContentResolver(),
                            Settings.Secure.ANDROID_ID));
                    return params;
                }
            };
            VolleySingleton.getInstance(maincontext).addToRequestQueue(stringRequest);

        }
    }  public static String faToEn(String num) {
        return num
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
    }


    private boolean isDataValid(String userName, String password, IValidationErrorListener validationErrorListener) {

        if (TextUtils.isEmpty(userName)) {

            validationErrorListener.emailError(ErrorCode.ENTER_EMAIL);
            return false;

        }

         if (TextUtils.isEmpty(password)) {

            validationErrorListener.passwordError(ErrorCode.ENTER_PASSWORD);
            return false;

        } else if (password.length() < 0) {

            validationErrorListener.passwordError(ErrorCode.PASSWORD_INVALID);
            return false;

        } else {
            return true;
        }
    }
}
