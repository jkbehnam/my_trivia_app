
package com.trivia.trivia.activities.Game.GameSellQuestion.SellQuestion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameSellQuestion.sellOkDialog;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.custom_widgets.NpaGridLayoutManager;
import com.trivia.trivia.custom_widgets.PaginationScrollListener;
import com.trivia.trivia.util.SellQuestion;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


public class FragmentSellQuestion_list extends BaseFragment implements ISellquestionview {
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.bt_filter)
    ImageView iv_filter;
    sellPaginationAdapter adapter;
    @BindView(R.id.empty_lay)
    LinearLayout empty_lay;
    private final int PAGE_START = 0;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    FragmentSellQuestionsPresenter fragmentSellQuestionsPresenter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    static ArrayList<SellQuestion> questionsList;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;
    AlertDialog ad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_questions, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        // setToolbar(rootView, "سوالات حل نشده");
        currentPage = 0;
        progressBar = rootView.findViewById(R.id.main_progress);
        errorLayout = rootView.findViewById(R.id.error_layout);
        btnRetry = rootView.findViewById(R.id.error_btn_retry);
        txtError = rootView.findViewById(R.id.error_txt_cause);
        adapter = new sellPaginationAdapter(getActivity());
        NpaGridLayoutManager layoutManager = new NpaGridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        //rcle.setItemAnimator(new DefaultItemAnimator());
        rcle.setAdapter(adapter);

        adapter.setOnCardClickListner(new sellPaginationAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position, SellQuestion q) {
                fragmentSellQuestionsPresenter.selectQuestion(q, position);

                sellOkDialog dt = new sellOkDialog();
                ad = dt.init(getActivity(),"از خریدت مطمئنی؟");
                ImageView btn_ok_alert = dt.getview().findViewById(R.id.alert_btn_ok);
                btn_ok_alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // fragmentQuestionsPresenter.sellQuestion(q,et_price_alert.getText().toString());
                        fragmentSellQuestionsPresenter.exchangeQuestion(q.getId(),position);
                    }
                });
                ad.show();
            }
        });
        rcle.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // loadNextPage();
                fragmentSellQuestionsPresenter.refreshEventList(currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentSellQuestionsPresenter.refreshEventList(currentPage);
            }
        });

        mProgressDialog = new ProgressDialog(c);
        setFragmentActivity(getActivity());

        fragmentSellQuestionsPresenter = new FragmentSellQuestionsPresenter(this,true);

        View bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });

        fragmentSellQuestionsPresenter.refreshEventList(PAGE_START);
        return rootView;

    }



    public void setItems(ArrayList<SellQuestion> eventsList) {
//questionsList=new ArrayList<>();


        questionsList = eventsList;
        int i = currentPage;
        if (eventsList.size() != 0) {
            if (currentPage < 1) {
                hideErrorView();
                progressBar.setVisibility(View.GONE);
                // if(this.questionsList==null)
                //  this.questionsList = questionsList;
                adapter.addAll(eventsList);
                // madapter = new adapter_game_questions(questionsList);
                // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
                // alphaAdapter.setFirstOnly(true);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            } else {
                adapter.removeLoadingFooter();
                isLoading = false;
                adapter.addAll(eventsList);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;

            }
        } else {
            adapter.removeLoadingFooter();

            // isLoading = false;

        }
        if (adapter.getItemCount() == 0) {
            empty_lay.setVisibility(View.VISIBLE);
        } else {
            empty_lay.setVisibility(View.GONE);
        }
    }

    @Override
    public void removeitem(int position) {
        // questionsList.remove(position);
        // rcle.getRecycledViewPool().clear();
        adapter.removeitem(position);
    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }


    /**
     * @param throwable required for {@link #fetchErrorMessage(Throwable)}
     * @return
     */
    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    // Helpers -------------------------------------------------------------------------------------


    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
     *
     * @return
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onStart() {


        super.onStart();
        //update your fragment

    }
    public void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        RadioRealButtonGroup radioRealButtonGroup_level = view.findViewById(R.id.radioRealButtonGroup_1);
        RadioRealButtonGroup radioRealButtonGroup_type = view.findViewById(R.id.radioRealButtonGroup_2);
        view.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q_type = null;

                switch (radioRealButtonGroup_type.getPosition()) {
                    case 1:
                        q_type = "ShortAnswer";

                        break;
                    case 2:
                        q_type = "MultipleChoice";

                        break;
                    case 0:
                        q_type = "DescAnswer";
                        break;
                    default:
                        q_type = "-1";
                        break;
                }
                String q_lvel = null;
                if (radioRealButtonGroup_level.getPosition() != -1) {
                    q_lvel = String.valueOf(3 - radioRealButtonGroup_level.getPosition());
                } else {
                    q_lvel = String.valueOf(radioRealButtonGroup_level.getPosition());
                }
                ArrayList<SellQuestion> q_list = new ArrayList<>();
                for (SellQuestion q : questionsList
                ) {
                    if (!q_type.equals("-1") && !q_lvel.equals("-1")) {
                        if (q.getQ_type().equals(q_type) && q.getQ_level().equals(q_lvel)) {
                            q_list.add(q);
                            setItems(q_list);
                        }
                    } else if (!q_type.equals("-1")) {
                        if (q.getQ_type().equals(q_type)) {
                            q_list.add(q);
                            setItems(q_list);
                        }
                    } else if (!q_lvel.equals("-1")) {
                        if (q.getQ_level().equals(q_lvel)) {
                            q_list.add(q);
                            setItems(q_list);
                        }
                    }


                }

            }
        });

        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);


        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }
    @Override
    public void dimissSellAlert() {
        ad.dismiss();
    }
}