
package com.trivia.trivia.home.gameActivity.Bank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterRcycleMain;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.buyQuestion.BuyQuestionFragment;
import com.trivia.trivia.home.gameActivity.FragmentChat.FragmentGroupMessage;
import com.trivia.trivia.home.questionList.Fragment_myQuestion_list;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.GameMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_bank extends myFragment {
    @BindView(R.id.MyGameMainActivity_recycle)
    RecyclerView rcleView;

    Event event;
    private static final String EVENT_KEY = "event_key";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_bank_mail, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        event = (Event) getArguments().getSerializable(
                EVENT_KEY);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcleView.setLayoutManager(layoutManager);
        ArrayList<GameMenu> glist = new ArrayList<>();
        glist.add(new GameMenu("واریز وجه سوال", "transaction"));


        adapterRcycleMain madapter = new adapterRcycleMain(glist);


        setToolbar(rootView, event.getE_name());
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcleView.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                switch (position) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
        return rootView;

    }


    public static Fragment_bank newInstance(Event event) {
        Fragment_bank fragment = new Fragment_bank();
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