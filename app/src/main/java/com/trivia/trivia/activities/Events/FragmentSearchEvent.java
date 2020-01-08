
package com.trivia.trivia.activities.Events;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_event_show_more;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.util.Tools;
import com.trivia.trivia.util.ViewAnimation;
import com.trivia.trivia.webservice.connectToServer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentSearchEvent extends BaseFragment implements Ieventview {
    FragmentActivity c;
    adapter_event_show_more madapter;


    FragmentEventPresenter fragmentEventPresenter;
    String user_id;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.bt_clear)
    ImageButton bt_clear;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.lyt_no_result)
    LinearLayout lyt_no_result;


    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;

    @BindView(R.id.lyt_suggestion)
    LinearLayout lyt_suggestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_toolbar_light, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        initToolbar(rootView, "جستجو");
        initComponent();
        setFragmentActivity(getActivity());
        PrefManager pm = new PrefManager(c);
        user_id = pm.getUserDetails().get("u_id");
        fragmentEventPresenter = new FragmentEventPresenter(this, user_id);


        return rootView;

    }

    private void initComponent() {

        et_search.addTextChangedListener(textWatcher);


        bt_clear.setVisibility(View.GONE);


        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchAction();
                    return true;
                }
                return false;
            }
        });

        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSuggestionSearch();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            }
        });
    }

    private void initToolbar(View rootView, String title) {

        // setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(getActivity(), R.color.grey_5);
        Tools.setSystemBarLight(getActivity());
    }

    public void setItems(ArrayList<Event> eventsList, int page) {
        if (eventsList.size() == 0) {
            lyt_no_result.setVisibility(View.VISIBLE);
        } else {
            lyt_no_result.setVisibility(View.GONE);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_event_show_more(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        rcle.setItemAnimator(new DefaultItemAnimator());
        madapter.setOnCardClickListner(new adapter_event_show_more.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, Event event) {

                if (event.getE_type().equals("گردهمایی")) {
                    DialogEventGathering dialog = new DialogEventGathering(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), user_id);
                    dialog.show();
                } else {
                    DialogEventGame dialog = new DialogEventGame(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), user_id);
                    dialog.show();
                }
            }
        });


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void showSuggestionSearch() {
        // madapter.refreshItems();
        ViewAnimation.expand(lyt_suggestion);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
            } else {
                bt_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        ViewAnimation.collapse(lyt_suggestion);
        lyt_no_result.setVisibility(View.GONE);
        final String query = et_search.getText().toString().trim();
        if (!query.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress_bar.setVisibility(View.GONE);
                    connectToServer.getEventsList(fragmentEventPresenter,user_id,0,query);

                }
            }, 2000);
            // madapter.addSearchHistory(query);
        } else {
            Toast.makeText(getActivity(), "فیلد ورودی را پر کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // finish();
        } else {
            //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void toast(String s) {

    }

    @Override
    public void start_non_concurrent_game_message(Event e, Group g) {

    }

    @Override
    public void payment(String u_id, String e_id) {

    }


    public void refreshlist() {
        fragmentEventPresenter.refreshEventList_registerd();
    }
}