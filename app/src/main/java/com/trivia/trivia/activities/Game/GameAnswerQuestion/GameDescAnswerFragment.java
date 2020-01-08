package com.trivia.trivia.activities.Game.GameAnswerQuestion;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.activities.Game.GameBank.PinEntryEditText;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GameDescAnswerFragment extends BaseFragment implements IGames {
    Question question;


    @BindView(R.id.fragment_multi_choice_iv_question)
    ImageView iv_question;

    @BindView(R.id.fragment_multi_choice_tv_question)
    TextView tvQTitle;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    @BindView(R.id.fragment_multi_choice_tv_2)
    TextView tv_im_question;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.et_answer)
    EditText et_answer;
    private static final String EVENT_KEY = "question_key";
    AnswerQuestionPresenter answerQuestionPresenter;

    View view;

    public static GameDescAnswerFragment newInstance(Question question) {
        GameDescAnswerFragment fragment = new GameDescAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, question);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_desc_answer, container, false);
        ButterKnife.bind(this, view);

        question = (Question) getArguments().getSerializable(
                EVENT_KEY);
        setFragmentActivity(getActivity());
//        setToolbar(view, "جواب کوتاه");
        Toast.makeText(getActivity(), "هر جواب اشتباه 20 درصد از امتیاز سوال کم میکند", Toast.LENGTH_SHORT).show();
        answerQuestionPresenter = new AnswerQuestionPresenter(this, question);
        answerQuestionPresenter.getMultipleChoice();
        answerQuestionPresenter.getAnswerLenght();
        connectToServer.getQuestionScore(answerQuestionPresenter, question.getId());

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager pm = new PrefManager(getActivity());
                pm.getUserDetails().get("u_id");
                answerQuestionPresenter.checkDescAnswer(et_answer.getText().toString(), question.getQ_id(), question.getId(), pm.getUserDetails().get("u_id"));

            }
        });

        return view;
    }


    @Override
    public void correcAnswer(int item) {
        Toasty.success(getActivity(), "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void wrongAnswer(int item) {
        Toasty.error(getActivity(), "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void setItems(MultipleChoicePage mpcp) {

    }

    @Override
    public void showLoading() {
        showLoading_base();
    }

    @Override
    public void hideLoading() {
        hideLoading_base();
    }

    @Override
    public void setscore(String score) {
        tvScore.setText("امتیاز سوال: "+score);
    }

    @Override
    public void finishFrag() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //    android.support.v4.app.FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                // android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //  fragmentTransaction.remove(fragment);
                //  fragmentTransaction.commit();

                getFragmentManager().popBackStack();
            }
        }, 800);
    }

    public void setLenght(String s) {
        PinEntryEditText.mNumChars = Integer.parseInt(s);
        initItem(Integer.parseInt(s));

    }

    @Override
    public void showanimation() {

    }

    public void initItem(int st) {
        if (question.getQ_img().isEmpty() || question.getQ_img() == null) {
            tvQTitle.setVisibility(View.VISIBLE);
            tvQTitle.setText(question.getQ_title());
            iv_question.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.GONE);
        } else {
            tvQTitle.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.VISIBLE);
            iv_question.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(question.getQ_img()).into(iv_question);
            tv_im_question.setText(question.getQ_title());
        }

    }
}
