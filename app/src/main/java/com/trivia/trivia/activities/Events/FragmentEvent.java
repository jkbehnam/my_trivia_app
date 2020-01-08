
package com.trivia.trivia.activities.Events;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_viewpager_MessagesEvents;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentEvent extends BaseFragment {
    View rootView;
    private Toolbar toolbar;
    private SlidingTabLayout tabLayout;
    static FragmentEvent fragmentEvent;
    AlertDialog ad;
    @BindView(R.id.mainImage)
    ImageView iv_main;
    @BindView(R.id.main_title)
    TextView tv_main_title;
    @BindView(R.id.main_subtitle)
    TextView tv_main_subtitle;
    @BindView(R.id.iv_search)
    ImageView iv_search;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, rootView);
        ViewPager viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout = rootView.findViewById(R.id.tl_1);

        setFragmentActivity(getActivity());
        setToolbar(rootView, "حلت");

        //========================
        if (Utils.isNetworkAvailable(getActivity())) {
//======================
            //darkhast esme moaref
            /*
            reagentDialog dt = new reagentDialog();
            ad = dt.init_dialog(getActivity(), rootView);
            ad.show();
            */
//======================

            setupViewPager(viewPager);
            tabLayout.setViewPager(viewPager);
            setCustomFont();
            viewPager.setCurrentItem(1);

            initToolbar();
            //connectToServer.getMainSetting(this);
        } else {
            Utils.displayCommonAlertDialog(getActivity(), "اتصال به اینترنت برقرار نمی باشد");
        }
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentSearchEvent();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter_viewpager_MessagesEvents adapter = new adapter_viewpager_MessagesEvents(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentRegisteredEvent(), "رویداد های من");
        adapter.addFragment(new FragmentUnregisteredEvent(), "همه رویداد ها");
        viewPager.setAdapter(adapter);

    }

    public static FragmentEvent newInstance() {
        if (fragmentEvent == null) {
            synchronized (FragmentEvent.class) {
                if (fragmentEvent == null) {
                    fragmentEvent = new FragmentEvent();
                }
            }
        }
        return fragmentEvent;
    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/iran_sans.ttf"));
                }
            }
        }
    }


    private void initToolbar() {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("رویداد ها");

    }

    public void set_main_img(String response) throws JSONException {
        JSONObject obj = new JSONObject(response);
        JSONObject userJson = obj.getJSONObject("setting");
        tv_main_title.setText(userJson.getString("title"));
        tv_main_subtitle.setText(userJson.getString("subtitle"));
        Glide.with(getActivity()).load(userJson.getString("url")).into(iv_main);
    }

}