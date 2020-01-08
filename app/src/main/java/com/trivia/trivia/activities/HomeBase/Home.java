package com.trivia.trivia.activities.HomeBase;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.crashlytics.android.Crashlytics;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.nkzawa.socketio.client.Socket;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameGroup.Fragment_group;
import com.trivia.trivia.activities.LoginRegisteration.login.LoginActivity;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.adapter.adapter_viewpager_main;
import com.trivia.trivia.base.BaseActivity;

import com.trivia.trivia.helper.NotificationHelper;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.custom_widgets.PaginationAdapterCallback;
import com.trivia.trivia.custom_widgets.my_AHBottomNavigation;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.ronash.pushe.Pushe;
import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;


import static com.trivia.trivia.util.URLs.URL_SET_SESSION;
import static com.trivia.trivia.webservice.connectToServer.getMainSetting;

public class Home extends BaseActivity implements PaginationAdapterCallback {
    my_AHBottomNavigation bottomNavigation;
    public static Context homecontext;
    public AHBottomNavigationViewPager viewPager;
    adapter_viewpager_main adapter;
    public static Socket socket = null;
    BroadcastReceiver updateUIReciver;
    BroadcastReceiver notif_button;
    boolean doubleBackToExitPressedOnce = false;
    static String user_id;
    public static boolean isRecursionEnable = true;

    //   DatabaseReference mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        homecontext = this;


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bottonNav));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.mainBackground)); //status bar or the time bar at the top

        }
        PrefManager pm = new PrefManager(this);
        user_id = pm.getUserDetails().get("u_id");
        if (pm.getUserDetails().get("name") == null) {

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            this.finish();
        }
        getMainSetting(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

            }
        });
        new AppUpdater(Home.this)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateXML("http://arithtopia.beigzadeh4behnam.ir/webservice/new_app/include/update.json")
                .setTitleOnUpdateAvailable("نسخه جدید آماده است")
                .setContentOnUpdateAvailable("برای ادامه حتما باید نسخه جدید را نصب کنید")
                .setButtonUpdate("دریافت")
                .setButtonDismiss("بعدا")
                .setButtonDoNotShowAgain(null)
                .setCancelable(false)
          
                .setButtonDismissClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .start();

        //startService(service);
        // Intent enableIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        //  startActivity(enableIntent);


        // Intent i = new Intent(this, ExampleJobIntentService.class);
        //  ExampleJobIntentService.enqueueWork(this,i);
        //FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
      /*  Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag("my-unique-tag")        // uniquely identifies the job
                .build();
*/
    /*    Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MyJobService.class)
                // uniquely identifies the job
                .setTag("my-unique-tag")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(2, 2))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                       // Constraint.ON_UNMETERED_NETWORK,
                        // only run when the device is charging
                      //  Constraint.DEVICE_CHARGING
                )
               // .setExtras(myExtrasBundle)
                .build();

        dispatcher.mustSchedule(myJob);

        dispatcher.cancel("my-unique-tag");
        dispatcher.mustSchedule(myJob);
        */

    /*    IntentFilter filter = new IntentFilter("com.mydefinepackage.myactivity");
        this.registerReceiver(new bcr_notfi(), filter);
        Intent i =new Intent("com.mydefinepackage.myactivity");
        sendBroadcast(i);
*/
        viewPager = findViewById(R.id.viewPagerMain);
//--------------------------------------------------------------
        bottomNavigation = findViewById(R.id.bottom_navigation);

        viewPager.setOffscreenPageLimit(3);
        adapter = new adapter_viewpager_main(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        bottomNavigation.setCurrentItem(0);
        viewPager.setCurrentItem(0, true);
        // FirebaseApp.initializeApp(this);
        // mydb = FirebaseDatabase.getInstance().getReference("message");
        bottomNavigation.setNotification(2, 1);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (viewPager.getCurrentItem() == position) {
                    //  adapter.set_clear();
                    // viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(position, true);
                } else
                    viewPager.setCurrentItem(position, true);
                // mydb.setValue("hello");
                return true;
            }
        });


        Pushe.initialize(this, true);
///--------------
   /*     PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("bcb4cd83b9d43fb93145", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
              //  Toast.makeText(Home.this, data, Toast.LENGTH_SHORT).show();
                tos(data);
            }
        });*/
///----------------------------------

/*mydb.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        tos(dataSnapshot.toString());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
*/


        //   pusher.connect();
        //=============
   /*      Socket socket = null;
         String Nickname ;
        //PrefManager pm = new PrefManager(getActivity());

        Nickname= pm.getUserDetails().get("u_id");
        try {

            socket = IO.socket("http://192.168.1.54:3000");

            //برقراری ارتباط

            socket.connect();

            socket.emit("join",Nickname);

        } catch (URISyntaxException e) {

            e.printStackTrace();

        }
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            String nickname1 = data.getString("senderNickname");
                            String message = data.getString("message");
                            Toast.makeText(Home.this, message, Toast.LENGTH_SHORT).show();

                            // make instance of message

                            //  Message m = new Message(nickname,message);


                            //add the message to the messageList

                            // MessageList.add(m);

                            // add the new updated list to the dapter
                            //  chatBoxAdapter = new ChatBoxAdapter(MessageList);

                            // notify the adapter to update the recycler view

                            //  chatBoxAdapter.notifyDataSetChanged();

                            //set the adapter for the recycler view

                            //     myRecylerView.setAdapter(chatBoxAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
        //=================

*/
        IntentFilter filter = new IntentFilter();

        filter.addAction("com.hello.action");

        updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //UI update here
                Bundle extras = intent.getExtras();

                getCurrentFragment(extras.getString("name"), extras.getString("message"), extras.getString("ename"));
            }
        };
        registerReceiver(updateUIReciver, filter);


        IntentFilter filter2 = new IntentFilter();

        filter2.addAction("com.hello.btnaction");

        notif_button = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //UI update here
                Bundle extras = intent.getExtras();

            }
        };
        registerReceiver(notif_button, filter2);

        checkAndRequestPermissions();
        send_session();





    }

    public void getCurrentFragment(String name, String message, String ename) {


        Fragment f2 = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (f2 instanceof Fragment_group) {

            ((Fragment_group) f2).addtochat(name, message, ename);

            // ((FragmentGroupMessage) f2).show_messages();
        } else {
            NotificationHelper.generateNotification(this, name + " در رویداد " + ename, message);
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermissions() {
        List<String> wantedPermissions = new ArrayList<>();
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_DENIED)
                wantedPermissions.add(Manifest.permission.FOREGROUND_SERVICE);
        } catch (Exception ignored) {
        }

        if (!wantedPermissions.isEmpty())
            ActivityCompat.requestPermissions(this, wantedPermissions.toArray(new String[wantedPermissions.size()]), 0);
    }

    void tos(String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(homecontext, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_home2;
    }

    @Override
    public void init() {

    }


    @Override
    public void retryPageLoad() {

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toasty.warning(this, "برای خروج دوباره کلیک کنید.", Toast.LENGTH_SHORT, true).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    void send_session() {


        PrefManager pm = new PrefManager(this);

        Map<String, String> param = new HashMap<String, String>();
        param.put("u_id", pm.getUserDetails().get("u_id"));
        param.put("device", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        param.put("state", "home");

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                if (result.equals("restart")) {
                    pm.clearSession();
                    finish();
                }
            }
        }, param, URL_SET_SESSION);
    }


}
