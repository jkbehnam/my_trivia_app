package com.trivia.trivia.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;


import com.trivia.trivia.R;
import com.trivia.trivia.util.App;
import com.trivia.trivia.util.LocaleManager;
import com.trivia.trivia.util.Utils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity2 extends AppCompatActivity {

    private static final String TAG = "BaseActivity2";

    @Override
    protected void attachBaseContext(Context base) {
        App.localeManager = new LocaleManager(base);

        super.attachBaseContext(CalligraphyContextWrapper.wrap(App.localeManager.setLocale(base)));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Log.d(TAG, "onCreate");
        Utils.resetActivityTitle(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResourcesInfo();

     //   TextView tv = findViewById(R.id.cache);
      //  tv.setText(Utility.getTitleCache());
    }

    private void showResourcesInfo() {
        Resources topLevelRes = Utils.getTopLevelResources(this);
       // updateInfo("Top level  ", findViewById(R.id.tv1), topLevelRes);

        Resources appRes = getApplication().getResources();
      //  updateInfo("Application  ", findViewById(R.id.tv2), appRes);

        Resources actRes = getResources();
      //  updateInfo("Activity  ", findViewById(R.id.tv3), actRes);

      //  TextView tv4 = findViewById(R.id.tv4);
        String defLanguage = Locale.getDefault().getLanguage();
      //  tv4.setText(String.format("Locale.getDefault() - %s", defLanguage));
      //  tv4.setCompoundDrawablesWithIntrinsicBounds(null, null, getLanguageDrawable(defLanguage), null);
    }

    private void updateInfo(String title, TextView tv, Resources res) {
        Locale l = LocaleManager.getLocale(res);
       // tv.setText(title + Utility.hexString(res) + String.format(" - %s", l.getLanguage()));
      //  Drawable icon = getLanguageDrawable(l.getLanguage());
       // tv.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

   /* private Drawable getLanguageDrawable(String language) {
        switch (language) {
            case LANGUAGE_ENGLISH:
                return ContextCompat.getDrawable(this, R.drawable.language_en);
            case LANGUAGE_FARSI:
                return ContextCompat.getDrawable(this, R.drawable.language_uk);
            default:
                Log.w(TAG, "Unsupported language");
                return null;
        }
    }*/

    public abstract int getLayout();

    public abstract void init();

}