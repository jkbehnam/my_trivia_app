package com.trivia.trivia.login;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.helper.HashedPassword;
import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trivia.trivia.login.LoginActivity.maincontext;

/**
 * Created by cstudioo on 06/01/17.
 */

public class LoginPresenterImpl implements ILoginPresenter {


    ILoginView mILoginView;
    ILoginInteractor mILoginInteractor;

    public LoginPresenterImpl(ILoginView loginView, ILoginInteractor loginInteractor) {
        this.mILoginView = loginView;
        this.mILoginInteractor = loginInteractor;
    }


    @Override
    public void callLogin(String username, String password) {
        mILoginView.showLoading();
        mILoginInteractor.login(username,  HashedPassword.create(faToEn(password),"dfdf").toString(), new ILoginInteractor.IValidationErrorListener() {
            @Override
            public void emailError(ErrorCode code) {
                mILoginView.hideLoading();
                mILoginView.setEmailError(code);
            }

            @Override
            public void passwordError(ErrorCode code) {
                mILoginView.hideLoading();
                mILoginView.setPasswordError(code);
            }

        }, new ILoginInteractor.IOnLoginFinishedListener() {
            @Override
            public void getUserData(String response) throws JSONException {
                final GsonBuilder builder = new GsonBuilder();

                final Gson gson = builder.create();

                // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
                JSONObject obj = new JSONObject(response);
                if (obj.getString("message").equals("Login successfull")) {
                    final ResponseLogin user = gson.fromJson(obj.getString("user"), ResponseLogin.class);
                    mILoginView.loginSuccess(user);
                } else if (obj.getString("message").equals("not register phonenum")) {
                    mILoginView.loginFailure(ErrorCode.LOGIN_FAILED);
                } else if (obj.getString("message").equals("Invalid username or password")) {
                    mILoginView.loginFailure(ErrorCode.LOGIN_FAILED);
                }
                mILoginView.hideLoading();

            }

            @Override
            public void errorMsg(String errorMsg) {
                mILoginView.hideLoading();
                mILoginView.loginFailure(errorMsg);
            }

        });
    }
    public static String faToEn(String num) {
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
}
