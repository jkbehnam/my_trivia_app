package com.trivia.trivia.activities.Events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sasanebrahimi.persiandatepicker.PersianDatePicker;
import com.trivia.trivia.R;


public class DialogPassword {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    Button btn_ok;
    PersianDatePicker pdp;
    TextView title;

    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Context context, String titletext) {
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.reagent_dialog, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView iv_close = (ImageView) view_alert_dialog_exit.findViewById(R.id.imageView14);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ald_exit.dismiss();
            }
        });

        title = (TextView) view_alert_dialog_exit.findViewById(R.id.textView20);
        title.setText(titletext);
        return ald_exit;
    }

    public void show() {
        ald_exit.show();


    }

    public View getview() {
        return view_alert_dialog_exit;
    }

}

