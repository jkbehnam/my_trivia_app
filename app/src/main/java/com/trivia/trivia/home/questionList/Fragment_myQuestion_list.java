
package com.trivia.trivia.home.questionList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.my_question_list;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.util.Question;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_myQuestion_list extends myFragment implements Iquestionview {
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    my_question_list madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.bt_filter)
    ImageView iv_filter;
    FragmentQuestionsPresenter fragmentQuestionsPresenter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_questions, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        // setToolbar(rootView, "سوالات حل نشده");
        mProgressDialog = new ProgressDialog(c);
        setFragmentActivity(getActivity());

        fragmentQuestionsPresenter = new FragmentQuestionsPresenter(this);
        fragmentQuestionsPresenter.refreshEventList();
        bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });

        return rootView;

    }

    public void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        ((ImageView) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
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

    public void setItems(ArrayList<Question> eventsList) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new my_question_list(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        madapter.setOnCardClickListner(new my_question_list.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                fragmentQuestionsPresenter.selectQuestion(eventsList.get(position));
            }
        });

    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }


}