package com.trivia.trivia.activities.Events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.adapter.adapter_game_gamers;
import com.trivia.trivia.adapter.adapter_gamer_event_rank;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.webservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.trivia.trivia.activities.HomeBase.Home.homecontext;
import static com.trivia.trivia.webservice.connectToServer.show_error_warning;


public class DialogEventEnd {
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

    ExpandableLinearLayout expandableLayout_detail;
String e_id;
    @SuppressLint("ResourceAsColor")
    public AlertDialog init(Context context, String title,String e_id) {
        this.e_id=e_id;
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.event_dialog, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert_tv_title = view_alert_dialog_exit.findViewById(R.id.alert_tv_title);
        alert_tv_title.setText(title);
                rcle = view_alert_dialog_exit.findViewById(R.id.event_list_rcle);
        event_detail_btn = view_alert_dialog_exit.findViewById(R.id.event_detail_btn);

        expandableLayout_detail = view_alert_dialog_exit.findViewById(R.id.expandableLayout_detail);
        event_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRankingList();
               // expandableLayout_detail.toggle();
            }
        });

        ImageView iv_close=(ImageView)view_alert_dialog_exit.findViewById(R.id.iv_close);
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
        if (!response.equals("expired")) {
            int rank_pos = 0;

            final GsonBuilder builder = new GsonBuilder();

            final Gson gson = builder.create();
            // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
            JSONObject obj = new JSONObject(response);
            ArrayList<OtherGamer> questions2 = new ArrayList<>();
            final OtherGamer[] questions = gson.fromJson(obj.getString("rank"), OtherGamer[].class);

            questions2.addAll(Arrays.asList(questions));
            setItems(questions2);


        }
    }

    public void getRankingList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RANKING,
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
                param.put("e_id", e_id);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }


}

