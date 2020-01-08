package com.trivia.trivia.activities.Messages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity;
import com.trivia.trivia.util.NewsArticle;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import saman.zamani.persiandate.PersianDate;

public class DetailActivity extends BaseActivity {
    public static final String PARAM_ARTICLE = "param-article";
    NewsArticle news;
    private static final String EVENT_KEY = "news_key";
    private boolean isSaved;

    @BindView(R.id.tv_news_title)
    TextView tv_news_title;
    @BindView(R.id.tv_news_source)
    TextView tv_news_source;
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
        news = (NewsArticle) getIntent().getSerializableExtra(
                EVENT_KEY);
        show_loading();
        connectToServer.getnewsitem(this,news.getId());




    }


public void reciveRequeset(String response) throws JSONException {
hide_Loading();
    final GsonBuilder builder = new GsonBuilder();

    final Gson gson = builder.create();
    // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
    JSONObject obj = new JSONObject(response);
    ArrayList<NewsArticle> questions2 = new ArrayList<>();
    final NewsArticle[] questions = gson.fromJson(obj.getString("news"), NewsArticle[].class);

    setupArticleAndListener(questions[0]);
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
    private void setupArticleAndListener(NewsArticle news) {


        Glide.with(this).load(news.getUrlToImage()).into(iv_news_image);


        /*
        new DownloadImageTask((ImageView) iv_news_image)
                .execute(news.getUrlToImage());
        */

        tv_news_title.setText(news.getTitle());
        tv_news_content.setText(news.getContent());
        PersianDate p=new PersianDate(Long.parseLong(news.getPublishedAt())*1000);
        tv_time.setText(p.toString());
        tv_news_source.setText(news.getAuthor());
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
        return R.layout.activity_detail;
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