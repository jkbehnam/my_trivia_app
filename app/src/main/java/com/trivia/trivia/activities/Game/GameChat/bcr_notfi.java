package com.trivia.trivia.activities.Game.GameChat;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;

public class bcr_notfi extends BroadcastReceiver {

    int p;
    Context context;

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        context = arg0;

   if(!isMyServiceRunning(SocketIOService.class)&&SocketIOService.isrepeat)
   startServiceByAlarm(arg0);

    }
    private void startServiceByAlarm(Context context)
    {
        Intent service = new Intent(context.getApplicationContext(), SocketIOService.class);
        service.putExtra(SocketIOService.EXTRA_EVENT_TYPE, SocketIOService.EVENT_TYPE_JOIN);
        service.putExtra(SocketIOService.EXTRA_USER_NAME, "behnam");
         service.setPackage(context.getPackageName());

       // context.startService(service);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            ContextCompat.startForegroundService(context,service);

        }
        else
        {
            context.startService(service);
        }

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}


