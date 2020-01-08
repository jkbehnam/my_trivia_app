package com.trivia.trivia.activities.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sasanebrahimi.persiandatepicker.PersianDatePicker;
import com.trivia.trivia.R;


public class getPriceDialog {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    ImageView btn_ok;
    PersianDatePicker pdp;

    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Context context) {
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.get_price_dialog, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView iv_close=(ImageView)view_alert_dialog_exit.findViewById(R.id.imageView14);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ald_exit.dismiss();
            }
        });
        return ald_exit;
    }
public View getview(){
        return view_alert_dialog_exit;
}
    public void show() {
        ald_exit.show();


    }


}

