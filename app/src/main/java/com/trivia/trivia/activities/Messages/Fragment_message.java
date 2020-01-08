
package com.trivia.trivia.activities.Messages;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_viewpager_MessagesEvents;
import com.trivia.trivia.base.BaseFragment;


public class Fragment_message extends BaseFragment {

    private SlidingTabLayout tabLayout;
    FragmentActivity c;
    static Fragment_message fh;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.exercise_fragment_message,container,false);
        setRetainInstance(true);
        c = getActivity();
        ViewPager viewPager = rootView.findViewById(R.id.viewpager2);
        tabLayout = rootView.findViewById(R.id.tabs2);
        fh=this;


        setupViewPager(viewPager);


      setToolbar(rootView,"خبر ها");

        setupViewPager(viewPager);
        tabLayout.setViewPager(viewPager);
        setCustomFont();
        viewPager.setCurrentItem(1);

        return rootView;
    }
    public static Fragment_message newInstance() {
        if (fh == null) {
            synchronized(Fragment_message.class) {
                if (fh == null) {
                    fh = new Fragment_message();
                }
            }
        }
        return fh;
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter_viewpager_MessagesEvents adapter = new adapter_viewpager_MessagesEvents(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_system_message(), "خبر ها");
        adapter.addFragment(new Fragment_my_message(), "اطلاعیه من");
        viewPager.setAdapter(adapter);

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
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/iran_sans.ttf"));
                }
            }
        }
    }
}