
package com.trivia.trivia.home.gameActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexfu.countdownview.CountDownView;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterRcycleMain;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.buyQuestion.BuyQuestionFragment;
import com.trivia.trivia.home.gameActivity.Bank.Fragment_bank;
import com.trivia.trivia.home.gameActivity.FragmentChat.FragmentGroupMessage;
import com.trivia.trivia.home.questionList.Fragment_myQuestion_list;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.GameMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_main_event extends myFragment {
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rcleView;
    @BindView(R.id.count_down)
    CountDownView cdt;
    Event event;
    private static final String EVENT_KEY = "event_key";
    @BindView(R.id.game_chat_iv)
    ImageView iv_chat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_game_mail, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        cdt.start();
        event = (Event) getArguments().getSerializable(
                EVENT_KEY);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rcleView.setLayoutManager(layoutManager);
        ArrayList<GameMenu> glist = new ArrayList<>();
        glist.add(new GameMenu("خرید سوال", "transaction"));
        glist.add(new GameMenu("حل سوال", "solved"));
        glist.add(new GameMenu("بانک", "wallet"));
        glist.add(new GameMenu("رتبه بندی", "rank"));
        glist.add(new GameMenu("مراجع", "books"));

        adapterRcycleMain madapter = new adapterRcycleMain(glist);

        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentGroupMessage());
            }
        });

        setToolbar(rootView, event.getE_name());
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
                        loadFragment(Fragment_bank.newInstance(event));
                        break;
                    case 3:
                        break;
                }
            }
        });
        return rootView;

    }


    public static Fragment_main_event newInstance(Event event) {
        Fragment_main_event fragment = new Fragment_main_event();
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
}