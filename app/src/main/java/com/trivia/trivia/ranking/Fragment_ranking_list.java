
package com.trivia.trivia.ranking;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterRcycleSource;
import com.trivia.trivia.adapter.adapter_gamers_list;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.Utils;
import com.trivia.trivia.home.source.FragmentSourcePresenter;
import com.trivia.trivia.home.source.PlayerActivity;
import com.trivia.trivia.util.OtherGamer;
import com.trivia.trivia.util.obj_film;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_ranking_list extends myFragment {
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    adapterRcycleSource madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.bt_filter)
    ImageView iv_filter;

    @BindView(R.id.name_toolbar)
    TextView name_toolbar;
    FragmentRankingPresenter fragmentRankingPresenter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_source, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        name_toolbar.setText("رتبه بندی");
        // setToolbar(rootView, "سوالات حل نشده");
        mProgressDialog = new ProgressDialog(c);
        setFragmentActivity(getActivity());

        fragmentRankingPresenter = new FragmentRankingPresenter(this);
        // fragmentQuestionsPresenter.refreshEventList();
        bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });
        fragmentRankingPresenter.refreshEventList();

        return rootView;

    }

    public void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        ((ImageView) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
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

    public void setItems(ArrayList<OtherGamer> dv_list) {


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);

        String p_code = "";
        adapter_gamers_list madapter;
        madapter = new adapter_gamers_list(dv_list, p_code);


        rcle.setAdapter(madapter);

    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }






}