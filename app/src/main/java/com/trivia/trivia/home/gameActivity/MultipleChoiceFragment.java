package com.trivia.trivia.home.gameActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_multiple_choice_game;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class MultipleChoiceFragment extends myFragment implements IGameMultipleChoice {
    private static final String EVENT_KEY = "question_key";
    Question question;
    FragmentActivity c;

    @BindView(R.id.fragment_multi_choice_iv_question)
    ImageView iv_question;
    @BindView(R.id.rcyc_multichoise)
    RecyclerView rcye_qustions;
    @BindView(R.id.fragment_multi_choice_tv_question)
    TextView tvQTitle;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    @BindView(R.id.fragment_multi_choice_tv_2)
    TextView tv_im_question;
    adapter_multiple_choice_game madapter;
    MultipleChoicePresenter multipleChoicePresenter;
    MultipleChoicePage mpcp;
    int first_score;
    ProgressDialog mProgressDialog;

    public static MultipleChoiceFragment newInstance(Question question) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, question);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        ButterKnife.bind(this, view);
        c = getActivity();
        mProgressDialog = new ProgressDialog(c);
        question = (Question) getArguments().getSerializable(
                EVENT_KEY);
        setToolbar(view, "سوال چند گزینه ای");
        multipleChoicePresenter = new MultipleChoicePresenter(this, question);
        multipleChoicePresenter.getMultipleChoice();
        first_score = Integer.parseInt(question.getQ_score());
        // first_score=500;
        tvScore.setText(String.valueOf(first_score) + "امتیاز سوال");


        return view;
    }

    @Override
    public void correcAnswer(int item) {
        mpcp.getMpi().get(item).setState(1);
        madapter.notifyItemChanged(item);

        Toasty.success(getActivity(), "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit();
            }
        }, 1000);

    }

    void exit() {
        //   getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void wrongAnswer(int item) {
        mpcp.getMpi().get(item).setState(2);
        madapter.notifyItemChanged(item);
        Toasty.error(getActivity(), "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
        first_score -= 100;
        tvScore.setText(String.valueOf(first_score) + "امتیاز سوال");
        if (first_score == 100) {
            Toasty.error(getActivity(), "امکان پاسخ گویی نیست", Toast.LENGTH_SHORT, true).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit();
                }
            }, 1000);
        }
    }

    @Override
    public void setScore() {

    }


    @Override
    public void setItems(MultipleChoicePage mpcp2) {

        mpcp = mpcp2;

        if (question.getQ_img().isEmpty() || question.getQ_img() == null) {
            tvQTitle.setVisibility(View.VISIBLE);
            tvQTitle.setText(question.getQ_title());
            iv_question.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.GONE);
        } else {
            tvQTitle.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.VISIBLE);
            iv_question.setVisibility(View.VISIBLE);
            Glide.with(c).load(question.getQ_img()).into(iv_question);
            tv_im_question.setText(question.getQ_title());
        }


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcye_qustions.setLayoutManager(layoutManager);
        madapter = new adapter_multiple_choice_game(mpcp.getMpi());
        rcye_qustions.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapter_multiple_choice_game.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                multipleChoicePresenter.checkAnswer(mpcp.getMpi().get(position).getTitle(), "", position);
            }
        });
    }

    public void showLoading() {

        mProgressDialog.setTitle("pleas");
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
