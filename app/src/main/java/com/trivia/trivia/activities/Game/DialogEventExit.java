package com.trivia.trivia.activities.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasanebrahimi.persiandatepicker.PersianDatePicker;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_gamer_event_rank;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.webservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.activities.HomeBase.Home.homecontext;
import static com.trivia.trivia.webservice.connectToServer.show_error_warning;


public class DialogEventExit {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    ImageView btn_ok;
    PersianDatePicker pdp;
    TextView alert_tv_title;
    RecyclerView rcle;
    Button event_detail_btn;
    Button alert_btn_ok;
    Fragment_main_event fragment_main_event;
    ExpandableLinearLayout expandableLayout_detail;
String e_id;
    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Fragment_main_event fragment_main_event,Context context, String title, String e_id) {
        this.e_id=e_id;
         this.fragment_main_event=fragment_main_event;
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.event_dialog_exit, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert_tv_title = view_alert_dialog_exit.findViewById(R.id.alert_tv_title);
        alert_tv_title.setText(title);
        event_detail_btn = view_alert_dialog_exit.findViewById(R.id.event_exit_btn);
        ImageView iv_close=(ImageView)view_alert_dialog_exit.findViewById(R.id.imageView14);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ald_exit.dismiss();
            }
        });

        event_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_end_time();
               // expandableLayout_detail.toggle();
            }
        });

        return ald_exit;
    }

    public View getview() {
        return view_alert_dialog_exit;
    }

    public void show() {
        ald_exit.show();


    }


    public void setItems(ArrayList<OtherGamer> dv_list) {

        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        rcle.setLayoutManager(layoutManager);

        String p_code = "";
        adapter_gamer_event_rank madapter;
        madapter = new adapter_gamer_event_rank(dv_list, p_code);


        rcle.setAdapter(madapter);
        expandableLayout_detail.toggle();
    }

    public void reciveRequeset(String response) throws JSONException {
        if (response.equals("ok")) {
            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            ald_exit.dismiss();
            fragment_main_event.exit_event();


        }
    }

    public void set_end_time() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SET_GAME_END,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                String s = e_id;
                param.put("g_id", Fragment_main_event.group.getG_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }


}

