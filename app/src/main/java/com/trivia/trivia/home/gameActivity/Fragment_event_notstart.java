
package com.trivia.trivia.home.gameActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.util.Event;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_event_notstart extends myFragment {
    @BindView(R.id.expandableLayout_group)
    ExpandableLinearLayout expandableLayout_group;
    @BindView(R.id.expandableLayout_detail)
    ExpandableLinearLayout expandableLayout_detail;
    @BindView(R.id.event_group_btn)
    Button group_btn;
    @BindView(R.id.event_detail_btn)
    Button detail_btn;
    Event event;


    private static final String EVENT_KEY = "event_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_event_notstart, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);

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
        return rootView;

    }


    public static Fragment_event_notstart newInstance(Event event) {
        Fragment_event_notstart fragment = new Fragment_event_notstart();
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