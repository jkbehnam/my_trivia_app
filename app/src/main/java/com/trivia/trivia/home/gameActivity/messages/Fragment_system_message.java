package com.trivia.trivia.home.gameActivity.messages;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_system_message_recycle;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Image;
import com.trivia.trivia.util.NewsArticle;
import com.trivia.trivia.util.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trivia.trivia.home.HomeBase.Home.homecontext;


public class Fragment_system_message extends myFragment {

    FragmentActivity c;
    adapter_system_message_recycle madapter;
    @BindView(R.id.recycle_)
    RecyclerView rcle;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout srf;
    ArrayList<NewsArticle> dv_list;
    View rootView;
    FragmentNewsPresenter fragmentNewsPresenter = null;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private ViewPager viewPager;
    private Handler handler = new Handler();
    private static int[] array_image_place = {
            R.drawable.h1,
            R.drawable.h2,
            R.drawable.h3,
            R.drawable.h4,
            R.drawable.h5,
    };

    private static String[] array_title_place = {
            "مسابقه شهر ریاضی دانش\u200Cآموزی کشوری در دانشگاه فردوسی مشهد",
            "مسابقه شهر ریاضی دانش\u200Cآموزی استانی در دانشگاه فردوسی مشهد",
            "مسابقه شهر ریاضی خانواده در دانشگاه فردوسی مشهد",
            "مسابقه شهر ریاضی خانواده در دانشگاه فردوسی مشهد",
            "مسابقه شهر ریاضی خانواده در دانشگاه فردوسی مشهد",
    };

    private static String[] array_brief_place = {

            "اسفند ۱۳۹۷",
            "شهریور ۱۳۹۷",
            "اسفند ۱۳۹۷",
            "شهریور ۱۳۹۷",
            "اسفند ۱۳۹۷",

    };

    @SuppressLint("ResourceAsColor")

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_system_message, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();

        fragmentNewsPresenter = new FragmentNewsPresenter(this);


        setFragmentActivity(getActivity());

        PrefManager pm = new PrefManager(c);


        initComponent();
        fragmentNewsPresenter.refreshEventList();

        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentNewsPresenter.refreshEventList();
            }
        });

        return rootView;

    }

    public void setItems(ArrayList<NewsArticle> eventsList) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapter_system_message_recycle(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapter_system_message_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news_key", eventsList.get(position));
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

    private void initComponent() {
        layout_dots = (LinearLayout) rootView.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(getActivity(), new ArrayList<Image>());

        final List<Image> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            Image obj = new Image();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = array_title_place[i];
            obj.brief = array_brief_place[i];
            items.add(obj);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);

        ((TextView) rootView.findViewById(R.id.title)).setText(items.get(0).name);
        ((TextView) rootView.findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                ((TextView) rootView.findViewById(R.id.title)).setText(items.get(pos).name);
                ((TextView) rootView.findViewById(R.id.brief)).setText(items.get(pos).brief);
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        startAutoSlider(adapterImageSlider.getCount());
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Image> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });

            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {


        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(homecontext);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }


    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }
}