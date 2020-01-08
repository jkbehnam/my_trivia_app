package com.trivia.trivia.activities.Messages;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_activities_recycle;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.NewsArticle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_my_message extends BaseFragment {
    FragmentActivity c;
    adapter_activities_recycle madapter;
    @BindView(R.id.recycle_)
    RecyclerView rcle;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    ArrayList<NewsArticle> dv_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_my_message_new, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();



        setFragmentActivity(getActivity());

        PrefManager pm = new PrefManager(c);


        int i = 0;
        NewsArticle dv = null;

        dv_list = new ArrayList<>();
        if (true) {
            do {
                dv = new NewsArticle();
                dv.setTitle("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست");

                dv.setUrlToImage("https://moviemag.ir/cache/36a0e5e057055fb027ad0f66d1f2a265_w150_h150_cp.jpg");
                // i = cr.getInt(cr.getColumnIndex("id"));
                dv.setContent("تست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . خب آدمایی هستن که فکر میکنن با ورود به پنل کاربری وبلاگ ساده ایتست یا آزمایش کردن رو فکر کنم همتون بدونید به چه معناست . تست در فرایند نرم افزار هم وجود داره . خب آدمایی هستن که فکر میکنن با ورود به پنل کاربری وبلاگ ساده ای");
                dv_list.add(dv);
                i++;
            } while (i<5);
            //} while ( cr.moveToNext() );
        }
        setItems(dv_list);
      /*  if (getContext() != null) {
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
            rcle.addItemDecoration(divider);
        }
        */
        return rootView;

    }

    public void setItems(ArrayList<NewsArticle> eventsList) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_activities_recycle(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapter_activities_recycle.OnCardClickListner() {
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


    }

    public void showLoading() {
        showLoading_base();
        srf.setRefreshing(true);
    }

    public void hideLoading() {
        hideLoading_base();
        srf.setRefreshing(false);

    }


}