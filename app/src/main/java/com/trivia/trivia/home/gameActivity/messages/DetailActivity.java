package com.trivia.trivia.home.gameActivity.messages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.util.NewsArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity2 {
    public static final String PARAM_ARTICLE = "param-article";
    NewsArticle news;
    private static final String EVENT_KEY = "news_key";
    private boolean isSaved;

    @BindView(R.id.tv_news_title)
    TextView tv_news_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_news_desc)
    TextView tv_news_desc;
    @BindView(R.id.tv_news_content)
    TextView tv_news_content;
    @BindView(R.id.iv_news_image)
    ImageView iv_news_image;
    @BindView(R.id.btn_read_full)
    Button btn_read_full;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        makeUiFullscreen();
        setupToolbar();
          setupArticleAndListener();

        news = (NewsArticle) getIntent().getSerializableExtra(
                EVENT_KEY);

    }



    private void makeUiFullscreen() {
        // When applying fullscreen layout, transparent bar works only for VERSION < 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //     this.getRoot().setFitsSystemWindows(true);
        }
        // Make UI fullscreen and make it load stable
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * Extracts Article from Arguments and Adds button listeners
     */
    private void setupArticleAndListener() {
        news = (NewsArticle) getIntent().getSerializableExtra(
                EVENT_KEY);

        Glide.with(this).load("https://moviemag.ir/cache/36a0e5e057055fb027ad0f66d1f2a265_w150_h150_cp.jpg").into(iv_news_image);
        tv_news_title.setText(news.getTitle());
        tv_news_content.setText(news.getContent());
        setupShareButton(news);


    }
    private void setupToolbar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }



    private void setupShareButton(final NewsArticle article) {
        btn_read_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String shareText = article.getTitle() + "\n" + article.getUrl();
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                intent.setType("text/plain");

                startActivity(intent);
            }
        });
    }

   /* private void setupButtonClickListener(final Article article) {
        binding.btnReadFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayout() {
        return R.layout.aaactivity_detail;
    }

    @Override
    public void init() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter_transition, R.anim.slide_down_animation);
    }


}