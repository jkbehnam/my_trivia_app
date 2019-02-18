
package com.trivia.trivia.home.gameActivity.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_system_message_recycle;
import com.trivia.trivia.util.Daily_system_massage;

import java.util.ArrayList;
import java.util.Collections;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class Fragment_my_message extends Fragment {
    ArrayList<ChatMessage> chat_list;
    FragmentActivity c;
    static Fragment_my_message fh;
    ChatView chatView;
    View rootView;
    ArrayList<Daily_system_massage> dv_list;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_system_message_new, container, false);
        setRetainInstance(true);
        c = getActivity();
        fh = this;


        rv = (RecyclerView) rootView.findViewById(R.id.recycle_);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        rv.setLayoutManager(mLayoutManager);
        rv.setNestedScrollingEnabled(false);

        dv_list = new ArrayList<>();

        Daily_system_massage dv = null;
        //  Cursor cr = DataBase.getInstance(c).getDb().rawQuery("select * from system_message order by id", null);
        // cr.moveToFirst();

        adapter_system_message_recycle madapter = null;
        // cr.moveToFirst();
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        // Long start_date = prefs.getLong("start_date", 0);
        // long daysDiff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start_date);

        int i = 0;


        dv = new Daily_system_massage();
        dv.setQuestion("شهر ریاضی 97");
        dv.setCode(1);
        dv.setDay(1);
        // i = cr.getInt(cr.getColumnIndex("id"));
        dv.setAnswer("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . ");
        dv_list.add(dv);

        dv = new Daily_system_massage();
        dv.setQuestion("مسابقات کامپیوتر 97");
        dv.setCode(1);
        dv.setDay(1);
        // i = cr.getInt(cr.getColumnIndex("id"));
        dv.setAnswer("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . ");
        dv_list.add(dv);


        Collections.reverse(dv_list);
      //  madapter = new adapter_system_message_recycle(dv_list);

        rv.setAdapter(madapter);


        return rootView;
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }


}