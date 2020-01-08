
package com.trivia.trivia.activities.Game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameBuyQuestion.BuyQuestionFragment;
import com.trivia.trivia.activities.Game.GameChat.SocketIOService;
import com.trivia.trivia.activities.Game.GameGamerQuestion.Fragment_myQuestion_list;
import com.trivia.trivia.activities.Game.GameGroup.Fragment_group;
import com.trivia.trivia.activities.Game.GameGuideSource.Fragment_source_list;
import com.trivia.trivia.activities.Game.GameRanking.Fragment_ranking_list;
import com.trivia.trivia.activities.Game.GameSellQuestion.Fragment_sell_buy;
import com.trivia.trivia.activities.Game.GameSolveQuestion.solvingListFragment;
import com.trivia.trivia.adapter.adapter_game_main;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.GameMenu;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.webservice.connectToServer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;


public class Fragment_main_event extends BaseFragment {
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rcleView;
    @BindView(R.id.infl)
    LinearLayout inf_lay;
    @BindView(R.id.main_balance)
    TextView balance;
    @BindView(R.id.main_inflation)
    TextView inflation;
    @BindView(R.id.time_text)
    TextView time_text;

    @BindView(R.id.cv_countdown)
    CountdownView countdownView;
    @BindView(R.id.event_name_tv)
    TextView event_name_tv;
    static public Event event;
    static public Group group;
    private static final String EVENT_KEY = "event_key";
    private static final String GROUP_KEY = "group_key";
    @BindView(R.id.game_chat_cv)
    CardView iv_chat;
    @BindView(R.id.name_toolbar)
    TextView name_toolbar;
    @BindView(R.id.team_members)
    CardView team_members;
    @BindView(R.id.iv_group)
    ImageView iv_group;
    @BindView(R.id.iv_exit)
    ImageView iv_exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_dashboard, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        setFragmentActivity(getActivity());


        event = (Event) getArguments().getSerializable(
                EVENT_KEY);
        String i = event.getE_duration();
        group = (Group) getArguments().getSerializable(
                GROUP_KEY);
        Calendar c = Calendar.getInstance();
        if (event.getE_custom_exit().equals("yes")) {
            iv_exit.setVisibility(View.VISIBLE);
        } else {
            iv_exit.setVisibility(View.GONE);
        }
        connectToServer.getAcountBalance(this, group);
Long exit = null;
        if (event.getE_game_type().equals("not_concurrent")) {
            //  long start = ((c.getTimeInMillis() / 1000 - Integer.parseInt(group.getG_start_time())) * 1000);
            exit=TimeUnit.HOURS.toMillis(Integer.parseInt(event.getE_duration())) - ((c.getTimeInMillis() / 1000 - Integer.parseInt(group.getG_start_time())) * 1000);
            countdownView.start(exit);

        } else if (event.getE_game_type().equals("concurrent")) {
            exit=((Integer.parseInt(event.getE_end_time())) - (c.getTimeInMillis() / 1000)) * 1000;
            countdownView.start(exit);
        }


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));

        rcleView.setLayoutManager(layoutManager);
        ArrayList<GameMenu> glist = new ArrayList<>();
        glist.add(new GameMenu("خرید سوال", "transaction"));
        glist.add(new GameMenu("حل سوال", "evaluation"));
        glist.add(new GameMenu("واریز به حساب", "wallet"));
        glist.add(new GameMenu("رتبه بندی", "award"));
        glist.add(new GameMenu("مراجع", "map"));
        glist.add(new GameMenu("سوال بازار", "wallet"));
        adapter_game_main madapter = new adapter_game_main(glist);

        iv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadFragment(Fragment_group.newInstance());

                Fragment fragment = Fragment_group.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad;
                DialogEventExit dt = new DialogEventExit();
                ad = dt.init(Fragment_main_event.this, getActivity(), "مطمئنی میخوای بازی رو تموم کنی؟", event.getE_id());


                ad.show();
            }
        });
        // setToolbar(rootView, event.getE_name());
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcleView.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapter_game_main.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                switch (position) {
                    case 0:
                        loadFragment(new BuyQuestionFragment());
                        break;
                    case 1:
                        loadFragment(new Fragment_myQuestion_list());
                        break;
                    case 2:
                        loadFragment(new solvingListFragment());
                        break;
                    case 3:
                        loadFragment(new Fragment_ranking_list());
                        break;
                    case 4:
                        loadFragment(new Fragment_source_list());
                        break;
                    case 5:
                        loadFragment(Fragment_sell_buy.newInstance());
                        break;
                }
            }
        });
        event_name_tv.setText(event.getE_name());
        team_members.setEnabled(false);
        // initToolbar();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/iran_sans.ttf");
        //   View viewItem = rcleView.getLayoutManager().findViewByPosition(1);
        //    CardView icon = (CardView) viewItem.findViewById(R.id.item_game_cv);

        //  RecyclerView.ViewHolder holder = rcleView.findViewHolderForAdapterPosition(2);
        //  CardView imageView = holder.itemView.findViewById(R.id.item_game_cv);

        GuideView a = new GuideView.Builder(getActivity())
                .setTitle("نرخ تورم")
                .setContentText("نرخ تورم متغیره\nبا توجه به تورم خرید و واریزهاتو انجام بده")
                .setTargetView(inf_lay)
                .setTitleTypeFace(font)
                .setContentTypeFace(font)
                .setDismissType(DismissType.anywhere)
                .build();
        SharedPreferences prefs = getActivity().getSharedPreferences("com.you.app", Context.MODE_PRIVATE);
        Boolean dialogShown = prefs.getBoolean("dialogShown", false);
        if (!dialogShown) {
            //Your show dialog code
            prefs.edit().putBoolean("dialogShown", true).commit();

            a.show();
        }


        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (a.isShowing()) {
                    a.dismiss();
                    return true;
                }
                return false;
            }
        });

        Intent service = new Intent(getActivity(), SocketIOService.class);
        //SocketIOService.home=getContext();
        SocketIOService.isrepeat = true;
        service.putExtra(SocketIOService.EXTRA_EVENT_TYPE, SocketIOService.EVENT_TYPE_JOIN);
        service.putExtra(SocketIOService.EXTRA_USER_NAME, "behnam");
        service.putExtra(SocketIOService.EXTRA_EVENT_ID, event.getE_id());
        service.putExtra(SocketIOService.EXTRA_GROUP_ID, group.getG_id());

        ContextCompat.startForegroundService(getActivity(), service);

        if (exit <= 0) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        return rootView;

    }


    public static Fragment_main_event newInstance(Event event, Group group) {
        Fragment_main_event fragment = new Fragment_main_event();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, event);
        bundle.putSerializable(GROUP_KEY, group);
        fragment.setArguments(bundle);
        return fragment;
    }

    /* private void initToolbar() {
         toolbar.setNavigationIcon(R.drawable.left_arrow);
         toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.toolbar_color), PorterDuff.Mode.SRC_ATOP);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
         ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
       //  Tools.setSystemBarColor(getActivity(), R.color.grey_5);
        // Tools.setSystemBarLight(getActivity());
     }*/
    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setBalance(String i) {
        balance.setText(i);

    }

    public void setInflation(String i) {
        inflation.setText(i);

    }

    public void exit_event() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {

        }
    }


}