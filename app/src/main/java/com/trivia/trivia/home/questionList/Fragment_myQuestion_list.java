
package com.trivia.trivia.home.questionList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.my_question_list;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.util.Question;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_myQuestion_list extends myFragment implements Iquestionview{
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    my_question_list madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    FragmentQuestionsPresenter fragmentQuestionsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_questions, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        setToolbar(rootView,"سوالات حل نشده");
        mProgressDialog =new ProgressDialog(c);
        setFragmentActivity(getActivity());
        fragmentQuestionsPresenter = new FragmentQuestionsPresenter(this);
        fragmentQuestionsPresenter.refreshEventList();
        return rootView;

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

        mProgressDialog.setTitle("please wait");
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


}