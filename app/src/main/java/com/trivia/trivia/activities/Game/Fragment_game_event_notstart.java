
package com.trivia.trivia.activities.Game;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

import static com.trivia.trivia.activities.HomeBase.Home.homecontext;
import static com.trivia.trivia.util.URLs.URL_CHANGE_GROUP;
import static com.trivia.trivia.util.URLs.URL_GET_USERGROUP;
import static com.trivia.trivia.util.URLs.URL_SET_SESSION;


public class Fragment_game_event_notstart extends BaseFragment {
    @BindView(R.id.cv_countdownViewTest2)
    CountdownView countDownTimer;
    @BindView(R.id.expandableLayout_group)
    ExpandableLinearLayout expandableLayout_group;
    @BindView(R.id.expandableLayout_detail)
    ExpandableLinearLayout expandableLayout_detail;
    @BindView(R.id.event_group_btn)
    Button group_btn;
    @BindView(R.id.event_detail_btn)
    Button detail_btn;
    @BindView(R.id.item_event_tv_main)
    TextView item_event_tv_main;

    @BindView(R.id.tv_location)
            TextView tv_location;
    @BindView(R.id.tv_info)
            TextView tv_info;

    @BindView(R.id.ed_group_id)
    EditText ed_group_id;
    @BindView(R.id.alert_btn_ok)
    ImageView alert_btn_ok;
    @BindView(R.id.imageView14)
    ImageView close;
    Event event;
    PrefManager pm;
Fragment fragment;
    private static final String EVENT_KEY = "event_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_event_notstart, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        fragment=this;
        event = (Event) getArguments().getSerializable(
                EVENT_KEY);
        group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_group.toggle();
            }
        });
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_detail.toggle();
            }
        });


        Calendar c = Calendar.getInstance();

        countDownTimer.start(((Integer.parseInt(event.getE_start_time())) -(c.getTimeInMillis() / 1000)) * 1000);
        item_event_tv_main.setText(event.getE_name());


        tv_info.setText(event.getE_description());
        try {
            tv_location.setText(event.getE_address());
        }catch (Exception e){
            tv_location.setVisibility(View.GONE);
        }
        get_group_id();
        alert_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_id();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
        return rootView;

    }


    public static Fragment_game_event_notstart newInstance(Event event) {
        Fragment_game_event_notstart fragment = new Fragment_game_event_notstart();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, event);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void get_group_id(){
         pm = new PrefManager(getActivity());

        Map<String, String> param = new HashMap<String, String>();


        param.put("u_id", pm.getUserDetails().get("u_id"));
        param.put("e_id", event.getE_id());

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                final GsonBuilder builder = new GsonBuilder();

                final Gson gson = builder.create();
                // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
                JSONObject obj = new JSONObject(result);

                final Group groups = gson.fromJson(obj.getString("group"), Group.class);
                ed_group_id.setText(groups.getG_id());
            }
        },param,URL_GET_USERGROUP);
    }


    void change_id(){
        pm = new PrefManager(getActivity());

        Map<String, String> param = new HashMap<String, String>();


        param.put("u_id", pm.getUserDetails().get("u_id"));
        param.put("e_id", event.getE_id());
        param.put("g_id", ed_group_id.getText().toString());
        param.put("e_mem", event.getE_group_members());

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                String message=obj.getString("group");
                if(message.equals("change_group")){
                    Toast.makeText(homecontext, "تغییرات با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("full_group")){
                    Toast.makeText(homecontext, "ظرفیت گروه پر است.", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("wrong_group")){
                    Toast.makeText(homecontext, "کد گروه اشتباه است.", Toast.LENGTH_SHORT).show();
                }
            }
        },param,URL_CHANGE_GROUP);
    }
}