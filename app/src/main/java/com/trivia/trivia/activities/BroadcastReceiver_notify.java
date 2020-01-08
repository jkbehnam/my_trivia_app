package com.trivia.trivia.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.trivia.trivia.activities.Game.GameChat.SocketIOService;

public class BroadcastReceiver_notify extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, SocketIOService.class);
        context.stopService(service);
        SocketIOService.isrepeat=false;
        Intent intent2 = new Intent(context.getApplicationContext(), BroadcastReceiver_notify.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1234, intent2, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
