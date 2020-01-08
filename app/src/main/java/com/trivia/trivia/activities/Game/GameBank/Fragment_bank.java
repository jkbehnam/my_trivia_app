
package com.trivia.trivia.activities.Game.GameBank;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_game_main;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.GameMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_bank extends BaseFragment {
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


        adapter_game_main madapter = new adapter_game_main(glist);


        setToolbar(rootView, event.getE_name());
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcleView.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapter_game_main.OnCardClickListner() {
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