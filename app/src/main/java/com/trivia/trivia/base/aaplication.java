package com.trivia.trivia.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import com.danikula.videocache.HttpProxyCacheServer;

import java.util.Locale;

public class aaplication extends Application {

    static aaplication application;
    private HttpProxyCacheServer cacheServer;

    @Override
    public void onCreate() {

        application=this;
        super.onCreate();
    }
    public static HttpProxyCacheServer getCacheServer(Context context) {
        if(application.cacheServer == null) application.cacheServer = application.buildHttpCacheServer();
        return application.cacheServer;
    }
    private HttpProxyCacheServer buildHttpCacheServer() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(getCacheDir())
                .build();
    }

}
