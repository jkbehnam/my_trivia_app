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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.sasanebrahimi.persiandatepicker.PersianDatePicker;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Profile.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trivia.trivia.webservice.connectToServer.getUserScore;


public class DialogPayCurrencyGathering {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    ImageView btn_ok;
    PersianDatePicker pdp;
    TextView alert_tv_title;

    Button event_money_btn;
    Button event_score_btn;
    Button alert_btn_ok;
    Fragment_main_event fragment_main_event;
    ExpandableLinearLayout expandableLayout_detail;
    RecyclerView rcl_member;
    String e_id;

    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Context context, String title, String e_id, String money, String score, String u_id) {
        this.e_id = e_id;
        this.fragment_main_event = fragment_main_event;
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.event_dialog_pay_gathering, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert_tv_title = view_alert_dialog_exit.findViewById(R.id.alert_tv_title);
        alert_tv_title.setText(title);
        event_money_btn = view_alert_dialog_exit.findViewById(R.id.pay_money);
        event_money_btn.setText("درگاه بانک");
        event_score_btn = view_alert_dialog_exit.findViewById(R.id.pay_score);
        event_score_btn.setText("حساب");
        getUserScore(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                JSONObject obj = new JSONObject(result);
                String u_score ;
                if (obj.getString("u_score") == null || obj.getString("u_score").isEmpty()) {
                    u_score = "0";
                } else {
                    u_score = obj.getString("u_score");
                }
                try {
                    event_score_btn.setText("حساب " + "(" + obj.getString("u_score") + " موجودی" + ")");
                    if (Integer.parseInt(u_score) < Integer.parseInt(score)) {
                        Toast.makeText(context, "موجودی کافی نیست", Toast.LENGTH_SHORT).show();
                        event_score_btn.setClickable(false);
                    } else {
                        event_score_btn.setClickable(true);
                    }
                } catch (Exception e) {
                    event_score_btn.setText("حساب " + "(" + "0" + " موجودی" + ")");
                   // Toast.makeText(context, "موجودی کافی نیست", Toast.LENGTH_SHORT).show();
                    event_score_btn.setClickable(true);
                }

            }
        }, u_id);


        ImageView iv_close = (ImageView) view_alert_dialog_exit.findViewById(R.id.imageView14);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ald_exit.dismiss();
            }
        });


        return ald_exit;
    }

    public View getview() {
        return view_alert_dialog_exit;
    }

}

