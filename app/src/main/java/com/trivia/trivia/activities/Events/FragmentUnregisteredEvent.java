
package com.trivia.trivia.activities.Events;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Events.multitype.MultiViewTypeAdapter;
import com.trivia.trivia.activities.Events.multitype.SectionDataModel;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentUnregisteredEvent extends BaseFragment implements Ieventview {
    PrefManager pm;
    FragmentActivity c;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    FragmentEventPresenter fragmentEventPresenter;
    ProgressDialog mProgressDialog;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    int item = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private static final String TAG = "event";
    private int TOTAL_PAGES = 5;
    @BindView(R.id.empty_lay)
    LinearLayout empty_lay;
    MultiViewTypeAdapter adapter;

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
        fragmentEventPresenter.refreshEventList(0);

        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentEventPresenter.refreshEventList(0);
            }
        });

        // createDummyData();
        return rootView;

    }

    // public void setItems(ArrayList<Event> eventsList,int page) {
     /*   GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_event_unregistered(eventsList);
        rcle.setAdapter(madapter);
*/
      /*  View loadingView = View.inflate(getActivity(), R.layout.layout_loading, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Endless endless = Endless.applyTo(rcle,
                loadingView
        );
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                endless.loadMoreComplete();
            }
        });
        */

      /*
        madapter.setOnRegBtnClickListner(new adapter_event_unregistered.OnRegBtnClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                fragmentEventPresenter.reg_game(pm.getUserDetails().get("u_id"), eventsList.get(position));
                item = position;
            }
        });

        madapter.notifyDataSetChanged();
        */
    //   }


    public void showLoading() {

     //   showLoading_base();
        srf.setRefreshing(true);
    }

    public void hideLoading() {
      //  hideLoading_base();
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
        String url = "http://arithtopia.beigzadeh4behnam.ir/webservice/Dashboard/Payment/payHandle.php";
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

        WebView URL1 = WebDialog1.findViewById(R.id.ticketline);
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
                fragmentEventPresenter.refreshEventList(0);
                // fragmentEventPresenter.refreshEventList_registerd();
                FragmentEventPresenter.fragment_registered_event.refreshlist();
            }
        });

    }

    public void setItems(ArrayList<Event> eventsList, int page) {

        rcle.setHasFixedSize(true);
        rcle.setNestedScrollingEnabled(true);
        ArrayList<SectionDataModel> allSampleData = new ArrayList<>();
        /*
        SectionDataModel dm2 = new SectionDataModel();
        dm2.setType(1);
        dm2.setHeaderTitle("");
        ArrayList<SingleItemModel> singleItemModels2 = new ArrayList<>();
        for (int j = 1; j <= 20; j++) {
            singleItemModels2.add(new SingleItemModel("Item " + j, "URL " + j));
        }
        dm2.setSingleItemModels(singleItemModels2);
        allSampleData.add(dm2);
*/
/*
        for (int i = 3; i >= 1; i--) {
            SectionDataModel dm = new SectionDataModel();
            dm.setType(i%3);
            dm.setHeaderTitle("رویدادهای ریاضی");
            ArrayList<SingleItemModel> singleItemModels = new ArrayList<>();
            for (int j = 1; j <= 20; j++) {
                singleItemModels.add(new SingleItemModel("Item " + j, "URL " + j));
            }
            dm.setAllEventInSection(eventsList);
            allSampleData.add(dm);
        }

*/
        SectionDataModel dm = new SectionDataModel();
        dm.setType(0);
        dm.setHeaderTitle("رویدادهای ریاضی");
        dm.setAllEventInSection(eventsList);
        allSampleData.add(dm);


        dm = new SectionDataModel();
        dm.setType(2);
        dm.setHeaderTitle("رویدادهای ریاضی");

        allSampleData.add(dm);


        adapter = new MultiViewTypeAdapter(allSampleData, getActivity());
        SnappyLinearLayoutManager linearLayoutManager = new SnappyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSnapType(SnapType.CENTER);
// Set the Interpolator
        linearLayoutManager.setSnapInterpolator(new AccelerateDecelerateInterpolator());
        linearLayoutManager.setStackFromEnd(true);

        rcle.setLayoutManager(linearLayoutManager);
        rcle.invalidate();

        rcle.setAdapter(adapter);

        adapter.setOnCardClickListner(new MultiViewTypeAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, Event event) {
                pauseVid();
                if (event.getE_type().equals("گردهمایی")) {
                    DialogEventGathering dialog = new DialogEventGathering(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), pm.getUserDetails().get("u_id"));
                    dialog.show();
                } else {
                    DialogEventGame dialog = new DialogEventGame(getActivity());
                    dialog.qrcode_reader(getActivity(), event, 5, getActivity(), pm.getUserDetails().get("u_id"));
                    dialog.show();
                }

            }

            @Override
            public void OnCardViewMoreClicked(View view) {
                Fragment fragment = new FragmentViewMoreEvent();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        if (adapter.getItemCount() == 0) {
            empty_lay.setVisibility(View.VISIBLE);
        } else {
            empty_lay.setVisibility(View.GONE);
        }

    }

    public void viewMoreItem() {
    }

    ;

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseVid();

    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVid();
    }
    void pauseVid(){
        if (adapter != null) {
try {
    adapter.onDetachedFromRecyclerView(rcle);
}catch (Exception e){}

        }
    }
}