package com.trivia.trivia.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.trivia.trivia.R;

public class App extends Application {

    private final String TAG = "App";

    // for the sake of simplicity. use DI in real apps instead
    public static LocaleManager localeManager;

    @Override
    protected void attachBaseContext(Context base) {
        localeManager = new LocaleManager(base);
        super.attachBaseContext(localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}