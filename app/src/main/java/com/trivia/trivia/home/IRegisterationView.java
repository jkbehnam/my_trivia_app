package com.trivia.trivia.home;

import com.trivia.trivia.util.ErrorCode;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

public interface IRegisterationView {
    void showLoading();
    void hideLoading();
    void startTimer();
    void EnterCodePage();
    void EnterphonePage();

    void showEndTimerDialog();
    void numberExisterror();
    void toastMessage(String message);


}
