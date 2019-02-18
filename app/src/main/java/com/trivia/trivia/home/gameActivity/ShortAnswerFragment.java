package com.trivia.trivia.home.gameActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.Bank.PinEntryEditText;
import com.trivia.trivia.util.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ShortAnswerFragment extends myFragment {
    Question question;
public static int lenght;
    @BindView(R.id.fragment_short_answer_tv_question)
    TextView tv_title;
    private static final String EVENT_KEY = "question_key";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultipleChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        View view = inflater.inflate(R.layout.fragment_short_answer, container, false);
        ButterKnife.bind(this, view);
        lenght=6;
        question = (Question) getArguments().getSerializable(
                EVENT_KEY);
        tv_title.setText(question.getQ_title());
setToolbar(view,"جواب کوتاه");

        PinEntryEditText et_answer = (PinEntryEditText) view.findViewById(R.id.txt_pin_entry);

        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("behnam")) {

                    Toasty.success(getActivity(), "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
                } else if (s.length() == "behnam".length()) {
                    Toasty.error(getActivity(), "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
                    et_answer.setText(null);
                }
            }
        });
        return view;
    }


    void exit() {
        //   getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}
