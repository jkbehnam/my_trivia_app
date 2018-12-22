package com.trivia.trivia.login;

import android.text.TextUtils;

import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.util.Utils;
import com.trivia.trivia.webservice.APIError;
import com.trivia.trivia.webservice.ErrorUtils;
import com.trivia.trivia.webservice.ServiceWrapper;
import com.trivia.trivia.webservice.model.request.Login;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cstudioo on 06/01/17.
 */

public class LoginInteractorImpl implements ILoginInteractor {

    private ServiceWrapper serviceInstance;

    @Override
    public void login(String userName, String passWord, IValidationErrorListener validationErrorListener, final IOnLoginFinishedListener loginFinishedListener) {
        if (isDataValid(userName, passWord, validationErrorListener)) {

            serviceInstance = new ServiceWrapper(null);

            Call<ResponseLogin> responseLoginCallback = serviceInstance.login(new Login(userName, passWord, ""));

            responseLoginCallback.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                    if (response.body() != null ) {
                        loginFinishedListener.getUserData(response.body());
                    } else {

                        if(response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            loginFinishedListener.errorMsg(error.getMessage());
                        } else {
                            loginFinishedListener.errorMsg("Problem getting user !! Try again later.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    loginFinishedListener.errorMsg("Problem getting user !! Try again later.");
                }
            });
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
