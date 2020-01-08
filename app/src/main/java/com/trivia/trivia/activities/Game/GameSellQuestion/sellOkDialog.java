package com.trivia.trivia.activities.Game.GameSellQuestion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sasanebrahimi.persiandatepicker.PersianDatePicker;
import com.trivia.trivia.R;


public class sellOkDialog {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    ImageView btn_ok;
    PersianDatePicker pdp;
    TextView alert_tv_title;

    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Context context, String title) {
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.sell_ok_dialog, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert_tv_title = view_alert_dialog_exit.findViewById(R.id.alert_tv_title);
        alert_tv_title.setText(title);


        return ald_exit;
    }

    public View getview() {
        return view_alert_dialog_exit;
    }

    public void show() {
        ald_exit.show();


    }


}

