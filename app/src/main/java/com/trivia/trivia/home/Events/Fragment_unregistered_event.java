
package com.trivia.trivia.home.Events;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.eventList_unregistered;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_unregistered_event extends myFragment implements Ieventview {
    PrefManager pm;
    FragmentActivity c;
    eventList_unregistered madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    FragmentEventPresenter fragmentEventPresenter;
    ProgressDialog mProgressDialog;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    int item = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_registered_events, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        c = getActivity();
        this.setFragmentActivity(c);
        setFragmentActivity(getActivity());
        pm = new PrefManager(c);

        fragmentEventPresenter = new FragmentEventPresenter(this, pm.getUserDetails().get("u_id"));
        fragmentEventPresenter.refreshEventList();

        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentEventPresenter.refreshEventList();
            }
        });
        return rootView;

    }

    public void setItems(ArrayList<Event> eventsList) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new eventList_unregistered(eventsList);
        rcle.setAdapter(madapter);
        madapter.setOnRegBtnClickListner(new eventList_unregistered.OnRegBtnClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                fragmentEventPresenter.reg_game(pm.getUserDetails().get("u_id"), eventsList.get(position));
                item = position;
            }
        });
        madapter.notifyDataSetChanged();
    }


    public void showLoading() {

        showLoading_base();
        srf.setRefreshing(true);
    }

    public void hideLoading() {
        hideLoading_base();
        srf.setRefreshing(false);
    }

    @Override
    public void toast(String s) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void start_non_concurrent_game_message(Event e, Group g) {

    }

    public void payment(String u_id, String e_id) {
        WebView webview = new WebView(getActivity());
        //  getActivity().setContentView(webview);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        String url = "http://topia.arith.land/webservice/Dashboard/Payment/payHandle.php";
        String postData = null;
        try {
            postData = "u_id=" + URLEncoder.encode(u_id, "UTF-8")
                    + "&e_id=" + URLEncoder.encode(e_id, "UTF-8")
                    + "&price=" + URLEncoder.encode("100", "UTF-8")
                    + "&des=" + URLEncoder.encode("شهر ریاضی", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // webview.setWebViewClient(new WebViewClient());
        //  webview.getSettings().setJavaScriptEnabled(true);
        //  webview.postUrl(url,postData.getBytes());
        //  webview.onFinishTemporaryDetach();
        //  webview.clearView();
        // webview.getSettings().setUseWideViewPort(true);
        //webview.getSettings().setLoadWithOverviewMode(true);

        toast("بعد از پرداخت لیست رویداد های ثبتنام شده را بروزرسانی کنید");

        Dialog WebDialog1 = new Dialog(getActivity());
        WebDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WebDialog1.setContentView(R.layout.web_layout);
        WebDialog1.setCancelable(true);

        WebView URL1 = (WebView) WebDialog1.findViewById(R.id.ticketline);
        URL1.setWebViewClient(new WebViewClient());
        URL1.setScrollbarFadingEnabled(true);
        URL1.setHorizontalScrollBarEnabled(false);
        URL1.getSettings().setJavaScriptEnabled(true);
        URL1.getSettings().setUserAgentString("First Webview");
        URL1.postUrl(url, postData.getBytes());
        WebDialog1.show();
        WebDialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                fragmentEventPresenter.refreshEventList();
                // fragmentEventPresenter.refreshEventList_registerd();
                FragmentEventPresenter.fragment_registered_event.refreshlist();
            }
        });

    }


}