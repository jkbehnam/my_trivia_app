package com.trivia.trivia.activities.Game.GameChat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.helper.NotificationHelper;
import com.trivia.trivia.activities.BroadcastReceiver_notify;
import com.trivia.trivia.activities.HomeBase.Home;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import co.intentservice.chatui.models.ChatMessage;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static com.trivia.trivia.activities.Game.GameChat.FragmentGroupMessage.chatURL;

/**
 * Created by planet on 6/8/2017.
 */

public class SocketIOService extends Service implements SocketEventListener.Listener, HeartBeat.HeartBeatListener {
    public static final String KEY_BROADCAST_MESSAGE = "b_message";
    public static final int EVENT_TYPE_JOIN = 1, EVENT_TYPE_MESSAGE = 2, EVENT_TYPE_TYPING = 3;
    private static final String EVENT_MESSAGE = "message";
    private static final String EVENT_JOIN = "join";
    private static final String EVENT_RECEIVED = "received";
    private static final String EVENT_TYPING = "typing";
    public static final String EXTRA_DATA = "extra_data_message";
    public static final String EXTRA_USER_NAME = "extra_user_name";
    public static final String EXTRA_EVENT_TYPE = "extra_event_type";
    public static final String EXTRA_GROUP_ID = "extra_group_id";
    public static final String EXTRA_EVENT_ID = "extra_event_id";
    public static final String EXTRA_EVENT_NAME = "extra_event_name";
    private static final String TAG = SocketIOService.class.getSimpleName();
    private Socket mSocket;
    private boolean mTyping;
    private WindowManager windowManager;
    private ServiceHandler mServiceHandler;
    private HeartBeat heartBeat;
    private String mUserId;
    public static Home home = null;
    public static boolean isrepeat = true;
    private ConcurrentHashMap<String, SocketEventListener> listenersMap;


    String group_id;
    String event_id;
    String event_name;
    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.arg1) {
                case 1:
                    Log.w(TAG, "Connected");
                    Toast.makeText(SocketIOService.this,
                            "متصل", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Log.w(TAG, "Disconnected");
                    //   Toast.makeText(SocketIOService.this,                            "قطع", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Log.w(TAG, "Error in Connection");
                    //    Toast.makeText(SocketIOService.this,                            "خطا در اتصال", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<ChatMessage> chatQueue = new ArrayList<>();
        listenersMap = new ConcurrentHashMap<>();
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread(TAG + "Args",
                THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        try {
            mSocket = IO.socket(chatURL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        getSocketListener();

        for (Map.Entry<String, SocketEventListener> entry : listenersMap.entrySet()) {
            mSocket.on(entry.getKey(), entry.getValue());
        }
        /*mSocket.on("user joined", new SocketEventListener("user joined", this));
        mSocket.on("user left", new SocketEventListener("user left", this));
        mSocket.on("typing", new SocketEventListener("typing", this));
        mSocket.on("stop typing", new SocketEventListener("stop typing", this));*/
        mSocket.connect();
        heartBeat = new HeartBeat(this);
        heartBeat.start();
    }

    private void getSocketListener() {
        listenersMap.put(Socket.EVENT_CONNECT, new SocketEventListener(Socket.EVENT_CONNECT, this));
        listenersMap.put(Socket.EVENT_DISCONNECT, new SocketEventListener(Socket.EVENT_DISCONNECT, this));
        listenersMap.put(Socket.EVENT_CONNECT_ERROR, new SocketEventListener(Socket.EVENT_CONNECT_ERROR, this));
        listenersMap.put(Socket.EVENT_CONNECT_TIMEOUT, new SocketEventListener(Socket.EVENT_CONNECT_TIMEOUT, this));
        listenersMap.put(EVENT_MESSAGE, new SocketEventListener(EVENT_MESSAGE, this));
        listenersMap.put(EVENT_RECEIVED, new SocketEventListener(EVENT_RECEIVED, this));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startid) {

        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");


        Intent snoozeIntent = new Intent(this, BroadcastReceiver_notify.class);
        snoozeIntent.setAction("com.hello.btnaction");

        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        Intent notificationIntent = new Intent(this, SocketIOService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, "1234")
                .setContentTitle("سرویس چت فعال است.")
                .setContentText("")
                .setSmallIcon(R.drawable.einstein)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_exit_to_app, "قطع سرویس",
                        snoozePendingIntent)
                .setAutoCancel(true)

                .build();
        notification.defaults = 0;


        startForeground(999999, notification);

        alarm();
        if (intent != null) {
            String chat = intent.getStringExtra(EXTRA_DATA);

            //String chat = intent.getStringExtra(EXTRA_DATA);
            int eventType = intent.getIntExtra(EXTRA_EVENT_TYPE, EVENT_TYPE_JOIN);
             group_id = intent.getStringExtra(EXTRA_GROUP_ID);
             event_id = intent.getStringExtra(EXTRA_EVENT_ID);
            event_name= intent.getStringExtra(EXTRA_EVENT_NAME);
            switch (eventType) {
                case EVENT_TYPE_JOIN:
                    mUserId = intent.getStringExtra(EXTRA_USER_NAME);
                    if (!mSocket.connected()) {
                        mSocket.connect();
                        Log.i(TAG, "connecting socket...");
                    } else {
                        joinChat(event_id,group_id);
                    }
                    break;
                case EVENT_TYPE_MESSAGE:
                    if (isSocketConnected()) {
                        sendMessage(chat, intent.getStringExtra(EXTRA_USER_NAME));
                        ///  QueryUtils.saveMessage(this, chat);
                    } else {
                        //  chatQueue.add(chat);
                    }
                    break;
                case EVENT_TYPE_TYPING:
                    if (isSocketConnected()) {
                        // sendMessage(chat, eventType);
                    }
                    break;
            }
        }
        return START_STICKY;
    }

    private boolean isSocketConnected() {
        if (null == mSocket) {
            return false;
        }
        if (!mSocket.connected()) {
            mSocket.connect();
            Log.i(TAG, "reconnecting socket...");
            return false;
        }

        return true;
    }

    @Override
    public void onHeartBeat() {
        if (mSocket != null && !mSocket.connected()) {
            mSocket.connect();
            Log.i(TAG, "connecting socket...");
        }
    }

    private void joinChat(String event_id,String group_id) {
        if (TextUtils.isEmpty(mUserId)) {
            // user name is null can not join chat
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", mUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//
        JSONObject jsonObject = new JSONObject();
        try {



            jsonObject.put("eventid", event_id);
            jsonObject.put("group", group_id);


            mSocket.emit("join", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        /// mSocket.emit(EVENT_JOIN, data);

        resendQueueMessages();
    }

    private void sendMessage(ChatMessage message, int event) {
        ///
       /* JSONObject chat = new JSONObject();
        try {
            chat.put("message_id", message.getId());
            chat.put("sender_id", message.getSenderId());
            chat.put("sender_name", message.getSenderName());
            chat.put("receiver_id", message.getReceiverId());
            if (!TextUtils.isEmpty(message.getChatMessage())) {
                chat.put("message", message.getChatMessage());
            }
            if (!TextUtils.isEmpty(message.getImageUrl())) {
                chat.put("image_url", CHAT_IMAGE_URL + message.getImageUrl());
            }
            chat.put("message_type", message.getMessageType().getValue());
            chat.put("date", message.getTime());
            chat.put("event", event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.w(TAG, "message sent " + chat.toString());
        mSocket.emit(EVENT_MESSAGE, chat);*/
    }

    private void sendMessage(String message, String Nickname) {
        /*JSONObject chat = new JSONObject();
        try {
            chat.put("message_id", message.getId());
            chat.put("sender_id", message.getSenderId());
            chat.put("sender_name", message.getSenderName());
            chat.put("receiver_id", message.getReceiverId());
            if (!TextUtils.isEmpty(message.getChatMessage())) {
                chat.put("message", message.getChatMessage());
            }
            if (!TextUtils.isEmpty(message.getImageUrl())) {
                chat.put("image_url", CHAT_IMAGE_URL + message.getImageUrl());
            }
            chat.put("message_type", message.getMessageType().getValue());
            chat.put("date", message.getTime());
            chat.put("event", event);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        Log.w(TAG, "message sent " + message);
        // mSocket.emit(EVENT_MESSAGE, chat);

        mSocket.emit("messagedetection", Nickname, message, group_id,event_id, event_name);

    }

    private void resendQueueMessages() {
        ///
   /*     ChatMessage chat = chatQueue.poll();
        if (chat != null) {
            sendMessage(chat, EVENT_TYPE_MESSAGE);
            resendQueueMessages();
        }*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        heartBeat.stop();
        mUserId = null;
        // clear chat queue if service stop
        //chatQueue.clear();
        for (Map.Entry<String, SocketEventListener> entry : listenersMap.entrySet()) {
            mSocket.off(entry.getKey(), entry.getValue());
        }
        Log.w(TAG, "onStop Service");


    }


    @Override
    public void onEventCall(String event, Object... args) {
        switch (event) {
            case Socket.EVENT_CONNECT:

                joinChat(event_id,group_id);
                android.os.Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = 1;
                mServiceHandler.sendMessage(msg);
                Boolean isConnected = true;
                break;
            case Socket.EVENT_DISCONNECT:
                Log.w(TAG, "socket disconnected");
                isConnected = false;
                msg = mServiceHandler.obtainMessage();
                msg.arg1 = 2;
                mServiceHandler.sendMessage(msg);
                break;
            case Socket.EVENT_CONNECT_ERROR:
                isConnected = false;
                msg = mServiceHandler.obtainMessage();
                msg.arg1 = 3;
                mServiceHandler.sendMessage(msg);
                // reconnect
                mSocket.connect();
                break;
            case Socket.EVENT_CONNECT_TIMEOUT:
                if (!mTyping) return;

                mTyping = false;
                mSocket.emit("stop typing");
                break;
            case EVENT_MESSAGE:
                JSONObject data = (JSONObject) args[0];
                try {
                    //extract data from fired event

                    String nickname1 = data.getString("senderNickname");
                    String message = data.getString("message");
                    String ename = data.getString("eventName");
                    Calendar c = Calendar.getInstance();

                    Intent local = new Intent();
                    local.putExtra("name", nickname1);
                    local.putExtra("message", message);
                    local.putExtra("ename", ename);
                    local.setAction("com.hello.action");

                    this.sendBroadcast(local);
                    ////
              /*  JSONObject data = (JSONObject) args[0];
                Log.w(TAG, "message : " + data.toString());
                try {
                    Intent intent = new Intent();
                    intent.setAction(KEY_BROADCAST_MESSAGE);
                    int messageEvent = data.getInt("event");
                    MessageType messageType = MessageType.getMessageType(data.getInt("message_type"));
                    String messageId = data.has("message_id") ? data.getString("message_id") : "";
                    String senderId = data.getString("sender_id");
                    String senderName = data.getString("sender_name");
                    String message = data.has("message") ? data.getString("message") : "";
                    String imageUrl = data.has("image_url") ? data.getString("image_url") : "";
                    String receiverId = data.getString("receiver_id");
                    long date = data.getLong("date");

                    if (messageEvent == EVENT_TYPE_MESSAGE) {
                        Message chat = new Message.Builder()
                                .messageId(messageId)
                                .receiverId(receiverId)
                                .senderId(senderId)
                                .senderName(senderName)
                                .message(message)
                                .imageUrl(imageUrl)
                                .messageType(messageType)
                                .time(date)
                                .build();
                        QueryUtils.saveMessage(this, chat);
                        //MessageUtils.playNotificationRingtone(getApplicationContext()); // play notification sound
                        if (!Utils.isChatActivityRunning(ChatActivity.class.getClass())) {
                            NotificationHelper.generateNotification(this, senderName, message);
                        }
                    }
                    intent.putExtra("receiver_id", receiverId);
                    intent.putExtra("sender_id", senderId);
                    intent.putExtra("sender_name", senderName);
                    intent.putExtra("event", messageEvent);
                    intent.putExtra("message_type", messageType.getValue());
                    sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //alarm();
        return super.onUnbind(intent);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
     /*   Intent restartServiceIntent = new Intent(getApplicationContext(), SocketIOService.class);
        restartServiceIntent.putExtra(SocketIOService.EXTRA_EVENT_TYPE, SocketIOService.EVENT_TYPE_JOIN);
        restartServiceIntent.putExtra(SocketIOService.EXTRA_USER_NAME, "behnam");
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.setExact(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
*/
        if (isrepeat) {
            Intent intent = new Intent(getApplicationContext(), bcr_notfi.class);
            intent.putExtra(SocketIOService.EXTRA_EVENT_TYPE, SocketIOService.EVENT_TYPE_JOIN);
            intent.putExtra(SocketIOService.EXTRA_USER_NAME, "behnam");
            //intent.setPackage(getPackageName());
            //intent.setAction(Intent.ACTION_MAIN);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 2);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        //isrepeat = true;
        super.onTaskRemoved(rootIntent);
    }

    public void alarm() {
        Intent intent = new Intent(getApplicationContext(), bcr_notfi.class);
        intent.putExtra(SocketIOService.EXTRA_EVENT_TYPE, SocketIOService.EVENT_TYPE_JOIN);
        intent.putExtra(SocketIOService.EXTRA_USER_NAME, "behnam");
        //intent.setPackage(getPackageName());
        //intent.setAction(Intent.ACTION_MAIN);
        // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 2);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
