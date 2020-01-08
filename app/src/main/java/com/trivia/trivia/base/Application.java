package com.trivia.trivia.base;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.danikula.videocache.HttpProxyCacheServer;
import com.trivia.trivia.util.ObjectBox;

public class Application extends android.app.Application {

    public static Application application;
    private HttpProxyCacheServer cacheServer;

    @Override
    public void onCreate() {

        application=this;
        createNotificationChannel();
        ObjectBox.init(this);
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
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager  manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel("1234");
            if(channel == null) {
                channel = new NotificationChannel("1234",
                        "examp",
                        NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
        }
    }

}

}
