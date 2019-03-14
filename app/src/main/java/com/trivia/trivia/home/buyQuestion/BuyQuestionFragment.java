package com.trivia.trivia.home.buyQuestion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.getLocation.getlocation;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


public class BuyQuestionFragment extends myFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.radioRealButtonGroup_1)
    RadioRealButtonGroup radioRealButtonGroup_level;
    @BindView(R.id.radioRealButtonGroup_2)
    RadioRealButtonGroup radioRealButtonGroup_type;

    @BindView(R.id.buy_question_score_tv)
    TextView tv_score;
    @BindView(R.id.buy_question_inflation_tv)
    TextView tv_inflation;

    @BindView(R.id.buy_question_btn)
    Button btn_buy;
    @BindView(R.id.buy_question_get_location_btn)
    Button btn_location;
    BuyQuestionPresenter buyQuestionPresenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultipleChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyQuestionFragment newInstance(String param1, String param2) {
        BuyQuestionFragment fragment = new BuyQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_buy_question, container, false);
        ButterKnife.bind(this, view);
        setFragmentActivity(getActivity());
        buyQuestionPresenter = new BuyQuestionPresenter(this);
        buyQuestionPresenter.getScore();
        init();

        return view;
    }

    void init() {

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyQuestionPresenter.buyQuestion(radioRealButtonGroup_level.getPosition(), radioRealButtonGroup_type.getPosition());
            }
        });
        btn_location.setVisibility(View.GONE);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_location();

            }
        });
    }

    void setPlayerScore(String i) {
        tv_score.setText("موجودی حساب: " + i);

    }

    void setinflation(String i) {
        tv_inflation.setText("نرخ تورم: " + i);

    }

    void get_location() {
        getlocation getlocation = new getlocation();
        getlocation.getlocation3(getContext());
    }

}
