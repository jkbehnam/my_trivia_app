
package com.trivia.trivia.activities.Game.GameGroup;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Game.GameChat.FragmentGroupMessage;
import com.trivia.trivia.activities.Game.GameGroup.GameGroupMembers.Fragment_members_list;
import com.trivia.trivia.adapter.adapter_viewpager_GroupChat;
import com.trivia.trivia.base.BaseFragment;


public class Fragment_group extends BaseFragment {

    private SlidingTabLayout tabLayout;
    FragmentActivity c;
    static Fragment_group fh;
FragmentGroupMessage fragmentGroupMessage;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_group, container, false);
        setRetainInstance(true);
        c = getActivity();
        ViewPager viewPager = rootView.findViewById(R.id.viewpager3);
        tabLayout = rootView.findViewById(R.id.tabs3);
        fh = this;
        setupViewPager(viewPager);
        setToolbar(rootView, "ارتباطات");

        setupViewPager(viewPager);
        tabLayout.setViewPager(viewPager);
        setCustomFont();
        viewPager.setCurrentItem(0);

        return rootView;
    }

    public static Fragment_group newInstance() {
        if (fh == null) {
            synchronized (Fragment_group.class) {
                if (fh == null) {
                    fh = new Fragment_group();
                }
            }
        }
        return fh;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter_viewpager_GroupChat adapter = new adapter_viewpager_GroupChat(getChildFragmentManager());
        fragmentGroupMessage=new FragmentGroupMessage();
       // adapter.addFragment(new FragmentGroupMessage(), "چت با همگروهی ها");
        adapter.addFragment(fragmentGroupMessage, "چت با همه");
        adapter.addFragment(new Fragment_members_list(), "اعضا");
        viewPager.setAdapter(adapter);

    }
public void addtochat(String nickname1, String message,String ename){
        if(Fragment_main_event.event.getE_name().equals(ename)){
        fragmentGroupMessage.addtochat( nickname1,  message);}
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