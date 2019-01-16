package com.trivia.trivia.home.solveQuestion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterAnswerList;
import com.trivia.trivia.util.Question;

import java.util.ArrayList;


public class solvingListFragment extends Fragment {
    protected String mTitle;
    private String state;
    RecyclerView rv_questions;
    FragmentActivity c;
    ArrayList<Question> g_list2;
SolvingListFragmentPresenter solvingListFragmentPresenter;
    public static solvingListFragment getInstance(String title, String state) {
        solvingListFragment sf = new solvingListFragment();
        sf.mTitle = title;
        sf.state = state;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        solvingListFragmentPresenter=new SolvingListFragmentPresenter(this);
        c = getActivity();
        rv_questions = (RecyclerView) v.findViewById(R.id.recycle_);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        rv_questions.setLayoutManager(mLayoutManager);
        rv_questions.setNestedScrollingEnabled(false);
       solvingListFragmentPresenter.setList();
        return v;
    }




public void setList(ArrayList<Question> g_list2){
    adapterAnswerList madapter;
    madapter = new adapterAnswerList(g_list2);
    rv_questions.setAdapter(madapter);

}
}