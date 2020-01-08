package com.trivia.trivia.activities.Game.GameSolveQuestion;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_game_solvedquestion;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.LineItemDecoration;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.Tools;

import java.util.ArrayList;
import java.util.List;


public class solvingListFragment extends BaseFragment {
    protected String mTitle;
    private String state;
    private adapter_game_solvedquestion madapter;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

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
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new LineItemDecoration(getActivity().getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        madapter = new adapter_game_solvedquestion(eventsList);

        //set data and list adapter

        recyclerView.setAdapter(madapter);
        madapter.setOnClickListener(new adapter_game_solvedquestion.OnClickListener() {
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
            madapter.setSelectedscore(0);
            actionMode = null;
            //  Tools.setSystemBarColor(((AppCompatActivity) getActivity()), R.color.red_600);
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = madapter.getSelectedItems();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i : selectedItemPositions
        ) {
            arrayList.add(madapter.getItem(i).getId());
        }
        solvingListFragmentPresenter.solvedQuestion(arrayList, selectedItemPositions);
     /*   for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
           // madapter.removeData(selectedItemPositions.get(i));
            arrayList.add( eventsList.get(i));
        }*/

        // madapter.notifyDataSetChanged();
    }

  /*  public void setList(ArrayList<Question> g_list2) {
        adapter_game_answerlist madapter;
        madapter = new adapter_game_answerlist(g_list2);
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
        int score = madapter.getSelectedscore();
        if (count == 0) {
            actionMode.finish();
            madapter.setSelectedscore(0);
        } else {
            actionMode.setTitle(count + "(" + score + " امتیاز" + ")");
            actionMode.invalidate();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


      //  Tools.setSystemBarColor(getActivity(), R.color.statusbar_color);

    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }

    public void refresh(List<Integer> selectedItemPositions) {
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            madapter.removeData(selectedItemPositions.get(i));
        }
        madapter.notifyDataSetChanged();
    }

}