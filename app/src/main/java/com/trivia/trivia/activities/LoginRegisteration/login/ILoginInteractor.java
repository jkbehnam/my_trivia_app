package com.trivia.trivia.activities.LoginRegisteration.login;

import com.trivia.trivia.util.ErrorCode;

import org.json.JSONException;

/**
 * Created by cstudioo on 05/01/17.
 */

public interface ILoginInteractor {

    void login(String userName, String passWord, IValidationErrorListener validationErrorListener, IOnLoginFinishedListener loginFinishedListener);

    interface IOnLoginFinishedListener {

        void getUserData(String user) throws JSONException;

        void errorMsg(String errorMsg);
    }

    interface IValidationErrorListener {

        void emailError(ErrorCode code);

        void passwordError(ErrorCode code);
    }
}
