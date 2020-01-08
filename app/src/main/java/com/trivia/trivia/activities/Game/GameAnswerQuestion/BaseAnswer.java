package com.trivia.trivia.activities.Game.GameAnswerQuestion;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.trivia.trivia.base.LoadingMain.Dialog_loading;

public class BaseAnswer {
    public AlertDialog ad;


    public void initLodingDialog(Context context2) {
        Dialog_loading aa;
        aa = new Dialog_loading();
        ad = aa.qrcode_reader(context2);
    }

    public void showLoadingBase() {
        try {
            ad.show();
        }catch (Exception e){}

    }

    public void hindLoadingBase() {
        try {
            ad.dismiss();
        }catch (Exception e) {}

    }
}
