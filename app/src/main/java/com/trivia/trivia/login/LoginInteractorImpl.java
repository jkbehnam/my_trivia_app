package com.trivia.trivia.login;

import android.text.TextUtils;

import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.util.Utils;

/**
 * Created by cstudioo on 06/01/17.
 */

public class LoginInteractorImpl implements ILoginInteractor {



    @Override
    public void login(String userName, String passWord, IValidationErrorListener validationErrorListener, final IOnLoginFinishedListener loginFinishedListener) {
        if (isDataValid(userName, passWord, validationErrorListener)) {
          /*  StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new com.android.volley.Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                          //  loginFinishedListener.getUserData(response.body());
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
                 //   params.put("username", faToEn(username));
                 //   params.put("password", faToEn(password));
                    return params;
                }
            };

       //     VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
*/
        }
    }


    private boolean isDataValid(String userName, String password, IValidationErrorListener validationErrorListener) {

        if (TextUtils.isEmpty(userName)) {

            validationErrorListener.emailError(ErrorCode.ENTER_EMAIL);
            return false;

        } else if(!Utils.isValidEmail(userName)){

            validationErrorListener.emailError(ErrorCode.EMAIL_INVALID);
            return false;

        } else if (TextUtils.isEmpty(password)) {

            validationErrorListener.passwordError(ErrorCode.ENTER_PASSWORD);
            return false;

        } else if (password.length() < 6) {

            validationErrorListener.passwordError(ErrorCode.PASSWORD_INVALID);
            return false;

        } else {
            return true;
        }
    }
}
