package com.trivia.trivia.home.gameActivity.messages;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;


import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_system_message_recycle;
import com.trivia.trivia.util.Daily_system_massage;
import com.trivia.trivia.util.NewsArticle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;




public class Fragment_system_message extends Fragment {

    FragmentActivity c;
    static Fragment_system_message fh;
    ArrayList<NewsArticle> dv_list;
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

        NewsArticle dv = null;
      //  Cursor cr = DataBase.getInstance(c).getDb().rawQuery("select * from system_message order by id", null);
       // cr.moveToFirst();

        adapter_system_message_recycle madapter = null;
       // cr.moveToFirst();
       // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
       // Long start_date = prefs.getLong("start_date", 0);
       // long daysDiff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start_date);

        int i = 0;

        if (true) {
            do {
                dv = new NewsArticle();
                dv.setTitle("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست ");

                dv.setUrlToImage("https://moviemag.ir/cache/36a0e5e057055fb027ad0f66d1f2a265_w150_h150_cp.jpg");
                // i = cr.getInt(cr.getColumnIndex("id"));
                dv.setContent("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . خب آدمایی هستن که فکر میکنن با ورود به پنل کاربری وبلاگ ساده ایتست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . خب آدمایی هستن که فکر میکنن با ورود به پنل کاربری وبلاگ ساده ای");
                dv_list.add(dv);
                i++;
            } while (i<5);
            //} while ( cr.moveToNext() );
        }



        Collections.reverse(dv_list);
        madapter = new adapter_system_message_recycle(dv_list);

        rv.setAdapter(madapter);
        if (getContext() != null) {
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
            rv.addItemDecoration(divider);
        }

        madapter.setOnCardClickListner(new adapter_system_message_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news_key", dv_list.get(position));
                intent.putExtras(bundle);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
               // binding.rvNewsPosts.setLayoutAnimation(controller);
               // binding.rvNewsPosts.scheduleLayoutAnimation();
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().overridePendingTransition(R.anim.slide_up_animation, R.anim.fade_exit_transition);
                }
            }
        });
       // DataBase_issend.system_messages_isread(MainActivity.mainContext,dv_list);
        return rootView;

    }



}