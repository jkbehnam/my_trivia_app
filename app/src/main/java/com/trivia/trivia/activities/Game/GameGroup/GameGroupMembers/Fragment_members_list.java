
package com.trivia.trivia.activities.Game.GameGroup.GameGroupMembers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameRanking.FragmentRankingPresenter;
import com.trivia.trivia.adapter.adapter_game_gamers;
import com.trivia.trivia.adapter.adapter_game_members;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Member;
import com.trivia.trivia.util.OtherGamer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_members_list extends BaseFragment {
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.bt_filter)
    ImageView iv_filter;

    @BindView(R.id.name_toolbar)
    TextView name_toolbar;


    FragmentMemberListPresenter fragmentRankingPresenter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        name_toolbar.setText("رتبه بندی");
        // setToolbar(rootView, "سوالات حل نشده");
        mProgressDialog = new ProgressDialog(c);
        setFragmentActivity(getActivity());

        fragmentRankingPresenter = new FragmentMemberListPresenter(this);
        // fragmentQuestionsPresenter.refreshEventList();
        View bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });

    /*    ArrayList<Member> dv_list=new ArrayList<>();
        dv_list.add(new Member("1","behnam","5","1","100"));
        dv_list.add(new Member("2","ali","3","8","120"));
        dv_list.add(new Member("3","amir","4","9","200"));
        setItems(dv_list);

     */
        fragmentRankingPresenter.refreshEventList();
        return rootView;

    }

    public void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        view.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });


        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);


        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    public void setItems(ArrayList<Member> dv_list) {


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        String p_code = "";
        adapter_game_members madapter;
        madapter = new adapter_game_members(dv_list, p_code);
        rcle.setAdapter(madapter);
        rcle.setVisibility(View.VISIBLE);

    }

    public void showLoading() {
        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }

public void showToast(String s){
    Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
}
}