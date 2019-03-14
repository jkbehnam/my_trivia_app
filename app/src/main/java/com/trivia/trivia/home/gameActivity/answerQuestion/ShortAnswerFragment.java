package com.trivia.trivia.home.gameActivity.answerQuestion;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.home.gameActivity.Bank.PinEntryEditText;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ShortAnswerFragment extends myFragment implements IGameMultipleChoice {
    Question question;


    @BindView(R.id.fragment_multi_choice_iv_question)
    ImageView iv_question;

    @BindView(R.id.fragment_multi_choice_tv_question)
    TextView tvQTitle;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    @BindView(R.id.fragment_multi_choice_tv_2)
    TextView tv_im_question;
    private static final String EVENT_KEY = "question_key";
    AnswerQuestionPresenter answerQuestionPresenter;
    PinEntryEditText et_answer=null;
    View view;

    public static ShortAnswerFragment newInstance(Question question) {
        ShortAnswerFragment fragment = new ShortAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, question);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_short_answer, container, false);
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

        return view;
    }


    @Override
    public void correcAnswer(int item) {
        Toasty.success(getActivity(), "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void wrongAnswer(int item) {
        Toasty.error(getActivity(), "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
        et_answer.setText(null);
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


        et_answer = (PinEntryEditText) view.findViewById(R.id.txt_pin_entry);
        // AutoWrapFilter.applyAutoWrap(et_answer,3);
        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if (s.toString().equals("behnambeigzadeh")) {

                    Toasty.success(getActivity(), "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
                } else if (s.length() == "behnambeigzadeh".length()) {
                    Toasty.error(getActivity(), "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
                    et_answer.setText(null);
                }*/
                if (s.length() == st) {
                    PrefManager pm = new PrefManager(getActivity());
                    pm.getUserDetails().get("u_id");
                    answerQuestionPresenter.checkShortAnswer(s.toString(), question.getQ_id(), question.getId(), pm.getUserDetails().get("u_id"));

                }
            }
        });
        et_answer.requestFocus();
    }
}
