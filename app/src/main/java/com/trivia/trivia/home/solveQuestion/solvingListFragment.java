package com.trivia.trivia.home.solveQuestion;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterAnswerList;
import com.trivia.trivia.adapter.my_solved_question_list;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.util.LineItemDecoration;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.Tools;

import java.util.ArrayList;
import java.util.List;


public class solvingListFragment extends myFragment {
    protected String mTitle;
    private String state;
    private RecyclerView recyclerView;
    private my_solved_question_list madapter;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Toolbar toolbar;

    FragmentActivity c;
    View v;
    // private ActionModeCallback actionModeCallback;
    private static final String EVENT_KEY = "question_key";
    SolvingListFragmentPresenter solvingListFragmentPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fr_simple_card, null);
        initToolbar();
        c = getActivity();
setFragmentActivity(c);
        solvingListFragmentPresenter = new SolvingListFragmentPresenter(this);

        solvingListFragmentPresenter.refreshEventList();

        return v;
    }


    public void initComponent(ArrayList<Question> eventsList) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        madapter = new my_solved_question_list(eventsList);

        //set data and list adapter

        recyclerView.setAdapter(madapter);
        madapter.setOnClickListener(new my_solved_question_list.OnClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                enableActionMode(pos);
             /*   if (madapter.getSelectedItemCount() > 0) {
                    enableActionMode(pos);
                } else {
                    // read the inbox which removes bold from the row
                    Question inbox = madapter.getItem(pos);
                   // Toast.makeText(getApplicationContext(), "Read: " + inbox.from, Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                enableActionMode(pos);
            }
        });

        actionModeCallback = new ActionModeCallback();

    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Tools.setSystemBarColor(getActivity(), R.color.blue_grey_700);
            mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
            mode.setTitle("حل سوال");

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                deleteInboxes();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            madapter.clearSelections();
            actionMode = null;
            Tools.setSystemBarColor(((AppCompatActivity) getActivity()), R.color.red_600);
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = madapter.getSelectedItems();
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i:selectedItemPositions
             ) {
            arrayList.add( madapter.getItem(i).getId());
        }
        solvingListFragmentPresenter.solvedQuestion(arrayList,selectedItemPositions);
     /*   for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
           // madapter.removeData(selectedItemPositions.get(i));
            arrayList.add( eventsList.get(i));
        }*/

       // madapter.notifyDataSetChanged();
    }

  /*  public void setList(ArrayList<Question> g_list2) {
        adapterAnswerList madapter;
        madapter = new adapterAnswerList(g_list2);
        recyclerView.setAdapter(madapter);

    }*/

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        madapter.toggleSelection(position);
        int count = madapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        Tools.setSystemBarColor(getActivity(), R.color.statusbar_color);

    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }
   public void refresh(List<Integer> selectedItemPositions){
       for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
           madapter.removeData(selectedItemPositions.get(i));
       }
       madapter.notifyDataSetChanged();
   }

}