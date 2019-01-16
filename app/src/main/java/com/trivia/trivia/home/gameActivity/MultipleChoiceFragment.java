package com.trivia.trivia.home.gameActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_multiple_choice_game;
import com.trivia.trivia.util.MultipleChoicePage;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MultipleChoiceFragment extends Fragment implements IGameMultipleChoice {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.rcyc_multichoise)
    RecyclerView rcye_qustions;
    @BindView(R.id.fragment_multi_choice_tv_question)
    TextView tvQTitle;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    adapter_multiple_choice_game madapter;
    MultipleChoicePresenter multipleChoicePresenter;
    MultipleChoicePage mpcp;
int first_score=300;
    public MultipleChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultipleChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultipleChoiceFragment newInstance(String param1, String param2) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        ButterKnife.bind(this, view);
        multipleChoicePresenter = new MultipleChoicePresenter(this);
        multipleChoicePresenter.settiems("");

        return view;
    }

    @Override
    public void correcAnswer(int item) {
        mpcp.getMpi().get(item).setState(1);
        madapter.notifyItemChanged(item);
        Toast.makeText(getContext(), "جواب صحیح است", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wrongAnswer(int item) {
        mpcp.getMpi().get(item).setState(2);
        madapter.notifyItemChanged(item);
        Toast.makeText(getContext(), "جواب غلط است", Toast.LENGTH_SHORT).show();
        first_score-=100;
        tvScore.setText(String.valueOf(first_score)+"امتیاز سوال");
        if(first_score==100){
            Toast.makeText(getContext(), "امکان پاسخ گویی نیست", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setScore() {

    }


    @Override
    public void setItems(MultipleChoicePage mpcp2) {

        mpcp = mpcp2;
        tvQTitle.setText(mpcp.getQ_title());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcye_qustions.setLayoutManager(layoutManager);
        madapter = new adapter_multiple_choice_game(mpcp.getMpi());
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcye_qustions.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapter_multiple_choice_game.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                multipleChoicePresenter.checkAnswer(mpcp.getMpi().get(position).getTitle(), "", position);
            }
        });
    }


    ;
    // TODO: Rename method, update argument and hook method into UI event


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
