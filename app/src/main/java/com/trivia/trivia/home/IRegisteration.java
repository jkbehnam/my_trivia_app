package com.trivia.trivia.home;

import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

public interface IRegisteration {
    void showLoading();
    void hideLoading();
    void setEmailError(ErrorCode code);
    void setPasswordError(ErrorCode code);
    void loginSuccess(ResponseLogin user);
    void loginFailure(ErrorCode code);
    void loginFailure(String errMsg);}
