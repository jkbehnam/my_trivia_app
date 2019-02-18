package com.trivia.trivia.home.solveQuestion;

import com.trivia.trivia.util.Question;

import java.util.ArrayList;

public class SolvingListFragmentPresenter {
    solvingListFragment solvingListFragment;
    public SolvingListFragmentPresenter(solvingListFragment solvingListFragment){
        this.solvingListFragment=solvingListFragment;
    }
   void setList(){
       ArrayList<Question> q=new ArrayList<>();
       q.add(new Question("سوال 1","","","","",""));
       q.add(new Question("سوال 2","","","","", ""));
       q.add(new Question("سوال 3","","","","",""));
       q.add(new Question("سوال 4","","","","",""));
       solvingListFragment.setList(q);

    }
}
