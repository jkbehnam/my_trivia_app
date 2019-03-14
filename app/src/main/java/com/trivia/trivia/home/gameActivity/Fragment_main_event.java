
package com.trivia.trivia.home.gameActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterRcycleMain;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.buyQuestion.BuyQuestionFragment;
import com.trivia.trivia.home.gameActivity.Bank.Fragment_bank;
import com.trivia.trivia.home.gameActivity.FragmentChat.FragmentGroupMessage;
import com.trivia.trivia.home.questionList.Fragment_myQuestion_list;
import com.trivia.trivia.home.solveQuestion.SolvingListFragmentPresenter;
import com.trivia.trivia.home.solveQuestion.solvingListFragment;
import com.trivia.trivia.home.source.Fragment_source_list;
import com.trivia.trivia.ranking.Fragment_ranking_list;
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


public class Fragment_main_event extends myFragment {
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rcleView;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dashboard_wallet, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        setFragmentActivity(getActivity());


        event = (Event) getArguments().getSerializable(
                EVENT_KEY);
        group = (Group) getArguments().getSerializable(
                GROUP_KEY);
        Calendar c = Calendar.getInstance();

        connectToServer.getAcountBalance(this,group);

        if(event.getE_end_type().equals("fixed")){
        if (event.getE_start_type().equals("not_concurrent")) {
         //   Long i=60 * 1000 * Integer.parseInt(event.getE_duration())  - ((c.getTimeInMillis() / 1000 - Integer.parseInt(group.getG_start_time())) * 1000);

            countdownView.start(TimeUnit.HOURS.toMillis(Integer.parseInt(event.getE_duration()))-((c.getTimeInMillis() / 1000 - Integer.parseInt(group.getG_start_time())) * 1000));
        } else {
            countdownView.start(TimeUnit.HOURS.toMillis(Integer.parseInt(event.getE_duration()))- - (c.getTimeInMillis() / 1000 - Integer.parseInt(event.getE_start_time())) * 1000);
        }}else if(event.getE_end_type().equals("not_fixed")) {
            countdownView.setVisibility(View.GONE);
            time_text.setVisibility(View.GONE);
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
        glist.add(new GameMenu("حل سوال", "solved"));
        glist.add(new GameMenu("واریز به حساب", "wallet"));
        glist.add(new GameMenu("رتبه بندی", "rank"));
        glist.add(new GameMenu("مراجع", "books"));

        adapterRcycleMain madapter = new adapterRcycleMain(glist);

        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentGroupMessage());
            }
        });

        // setToolbar(rootView, event.getE_name());
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcleView.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
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
                }
            }
        });
        event_name_tv.setText(event.getE_name());
        team_members.setEnabled(false);
        // initToolbar();
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
    public void setBalance(String i){
        balance.setText("موجودی حساب: "+i);

    }
    public void setInflation(String i){
        inflation.setText("نرخ تورم: "+i);

    }



}