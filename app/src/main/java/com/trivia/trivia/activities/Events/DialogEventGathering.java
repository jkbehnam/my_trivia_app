package com.trivia.trivia.activities.Events;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.activities.Profile.reg_data;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import saman.zamani.persiandate.PersianDate;

import static com.trivia.trivia.activities.LoginRegisteration.login.LoginPresenterImpl.faToEn;
import static com.trivia.trivia.webservice.connectToServer.reg_gamer_event_score;


public class DialogEventGathering extends Dialog implements View.OnClickListener, IeventAlertview {
    Event event;
    Context context;
    Dialog dialog;
    ImageView btn_ok_alert;
    EditText et_price_alert;
    AlertDialog ad;
    String prof;
    FragmentActivity fragmentActivity;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.event_title)
    TextView event_title;
    @BindView(R.id.event_descript)
    TextView event_descript;

    @BindView(R.id.event_price)
    TextView event_price;

    @BindView(R.id.event_proof)
    TextView event_proof;

    @BindView(R.id.event_lenght)
    TextView event_lenght;

    @BindView(R.id.event_start)
    TextView event_start;

    @BindView(R.id.event_end)
    TextView event_end;

    @BindView(R.id.event_members)
    TextView event_members;

    @BindView(R.id.event_btn_submit)
    TextView event_btn_submit;

    @BindView(R.id.event_fab_submit)
    FloatingActionButton event_fab_submit;

    @BindView(R.id.event_bookmark)
    ImageView event_bookmark;

    @BindView(R.id.event_share)
    ImageView event_share;

    @BindView(R.id.tv_reg_deadline)
    TextView tv_reg_deadline;
    @BindView(R.id.event_capacity)
    TextView event_capacity;
    String u_id;
    //@BindView(R.id.fragment_multi_choice_cv_question)
    // CardView cv_answer;
    // @BindView(R.id.scrollv)
    // ScrollView scrollv;
    FragmentAlertEventPresenter fragmentAlertEventPresenter;
    int position;

    public DialogEventGathering(@NonNull Context context) {
        super(context);
    }


    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context, Event event, int position, FragmentActivity fragmentActivity, String u_id) {
        this.context = context;
        this.event = event;
        this.position = position;
        this.fragmentActivity = fragmentActivity;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.u_id = u_id;

        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_event_gathering);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //   dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        ButterKnife.bind(this, dialog);

        Toolbar toolbar = dialog.findViewById(R.id.toolbar);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setTitle(null);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // answerQuestionPresenter.getAnswerLenght();
        //  connectToServer.getQuestionScore(answerQuestionPresenter, question.getId());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(dialog.getContext().getResources().getColor(R.color.white));
            dialog.getWindow().setNavigationBarColor(dialog.getContext().getResources().getColor(R.color.bottonNav));
        }
        init_items(event);
        event_btn_submit.setOnClickListener(this::onClick);
        event_fab_submit.setOnClickListener(this::onClick);
        fragmentAlertEventPresenter = new FragmentAlertEventPresenter(this, u_id);
        fragmentAlertEventPresenter.checkUserDetils();

        getEventCapacity();

        return dialog;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = fragmentActivity.getMenuInflater();
        inflater.inflate(R.menu.menu_article_share_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //finish();
            Toast.makeText(context, "share", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "share", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void show() {
        dialog.show();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_btn_submit:
            case R.id.event_fab_submit:
                submit_click();

                break;
            case R.id.event_share:
                break;
            case R.id.event_bookmark:
                break;
        }
    }

    public void init_items(Event event) {
        event_title.setText(event.getE_name());

        PersianDate persianDate2 = new PersianDate(Long.parseLong(event.getE_reg_deadline()) * 1000);
        Long l = persianDate2.getDayuntilToday();

        tv_reg_deadline.setText(l + "روز مانده");
        event_descript.setText(event.getE_description());
        event_price.setText(event.getE_price() + " تومان");


        PrefManager pm;
        pm = new PrefManager(context);
        prof = String.valueOf(Integer.parseInt(event.getE_price()) * Integer.parseInt(pm.get_setting().get("prof_toman")));
        event_proof.setText(prof + " پروف");

        try {
            PersianDate persianDate = new PersianDate(Long.parseLong(event.getE_gather_start_time()) * 1000);
            event_start.setText(persianDate.getShDay() + " " + persianDate.monthName() + " " + persianDate.getHour() + ":" + persianDate.getMinute());
        } catch (Exception e) {
            event_start.setText("شروع دلخواه");

        }

        //event_end.setText(event.getE_end_time());

        PersianDate persianDate = new PersianDate(Long.parseLong(event.getE_gather_end_time()) * 1000);
        event_end.setText(persianDate.getShDay() + " " + persianDate.monthName() + " " + persianDate.getHour() + ":" + persianDate.getMinute());
        event_lenght.setText(event.getE_duration());
        event_members.setText(event.getE_group_members());
        Glide.with(context).load(event.getE_img()).into(image);
    }
    public void submit_click() {
        if (event.getE_password().equals("") || event.getE_password() == null || event.getE_password().isEmpty()) {

            ifpay();
        } else {

            AlertDialog ad;
            DialogPassword dt = new DialogPassword();
            ad = dt.init(context, "این رویداد نیاز به رمز عبور دارد");


            EditText editt = (EditText) dt.getview().findViewById(R.id.editText2);
            ImageView okBtn = (ImageView) dt.getview().findViewById(R.id.alert_btn_ok);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (faToEn(editt.getText().toString()).equals(event.getE_password())) {
                        ifpay();
                        ad.dismiss();
                    } else {
                        Toast.makeText(context, "رمز اشتباه است", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ad.show();
        }


    }

    public void ifpay() {

        if (checkProfileDetails()) {
            showPaydialog();

        } else {
            Toast.makeText(context, "ابتدا اطلاعات فردی خود را کامل کنید.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this.context, reg_data.class);
            context.startActivity(i);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toast(String s) {

    }

    public boolean checkProfileDetails() {
        PrefManager pm;

        pm = new PrefManager(context);
        String sd = pm.get_user().get("user_detail");
        return !sd.equals("");
    }

    public void payment(String u_id, String e_id) {
        WebView webview = new WebView(context);
        //  getActivity().setContentView(webview);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        String url = "http://arithtopia.beigzadeh4behnam.ir/webservice/Dashboard/Payment/payHandle.php";
        String postData = null;
        try {
            postData = "u_id=" + URLEncoder.encode(u_id, "UTF-8")
                    + "&e_id=" + URLEncoder.encode(e_id, "UTF-8")
                    + "&price=" + URLEncoder.encode("200", "UTF-8")
                    + "&des=" + URLEncoder.encode("شهر ریاضی", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // webview.setWebViewClient(new WebViewClient());
        //  webview.getSettings().setJavaScriptEnabled(true);
        //  webview.postUrl(url,postData.getBytes());
        //  webview.onFinishTemporaryDetach();
        //  webview.clearView();
        // webview.getSettings().setUseWideViewPort(true);
        //webview.getSettings().setLoadWithOverviewMode(true);

        Toast.makeText(context, "بعد از پرداخت لیست رویداد های ثبتنام شده را بروزرسانی کنید", Toast.LENGTH_SHORT).show();

        Dialog WebDialog1 = new Dialog(context);
        WebDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WebDialog1.setContentView(R.layout.web_layout);
        WebDialog1.setCancelable(true);

        WebView URL1 = WebDialog1.findViewById(R.id.ticketline);
        URL1.setWebViewClient(new WebViewClient());
        URL1.setScrollbarFadingEnabled(true);
        URL1.setHorizontalScrollBarEnabled(false);
        URL1.getSettings().setJavaScriptEnabled(true);
        URL1.getSettings().setUserAgentString("First Webview");
        URL1.postUrl(url, postData.getBytes());
        WebDialog1.show();
        WebDialog1.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //  fragmentEventPresenter.refreshEventList(0);
                //    FragmentEventPresenter.fragment_registered_event.refreshlist();
            }
        });
        WebDialog1.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                checkPaymentSuccess();
            }
        });

    }

    @Override
    public void showPaydialog() {
        DialogPayCurrencyGathering dt = new DialogPayCurrencyGathering();
        ad = dt.init(context, "تعداد بلیت را انتخاب کنید.", "1", event.getE_price() + " تومان", prof, u_id);
        Button event_money_btn = dt.getview().findViewById(R.id.pay_money);
        Button event_score_btn = dt.getview().findViewById(R.id.pay_score);
        event_money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // connectToServer.regUserEvent(DialogEventGathering.this, u_id, event.getE_id());
                payment(u_id, event.getE_id());
            }
        });
        event_score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reg_gamer_event_score(new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {

                        // JSONObject obj = new JSONObject(result);
                        if (result.equals("ok")) {

                            Toast.makeText(context, "عملیات با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                            ad.dismiss();
                            dialog.dismiss();
                        }
                    }


                }, u_id, event.getE_id());

                // fragmentQuestionsPresenter.sellQuestion(q,et_price_alert.getText().toString(),position);
            }
        });
        ad.show();
        ad.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                checkPaymentSuccess();
            }
        });
    }

    void getEventCapacity() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("e_id", event.getE_id());

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                String message = obj.getString("number");
                try {
                    event_capacity.setText(String.valueOf(Integer.parseInt(event.getE_Reg_max_num()) - Integer.parseInt(message)));
                } catch (Exception e) {
                }
                ;

            }
        }, param, URLs.URL_GET_EVENT_CAPACITY);

    }


    void checkPaymentSuccess() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("e_id", event.getE_id());
        param.put("u_id", u_id);

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                String message = obj.getString("message");

                paymentReaction(message);
            }
        }, param, URLs.URL_CHECK_PAYMENT_SUCCESS);
    }

    void paymentReaction(String s) {
        if (s.equals("success")) {
            dialog.dismiss();
        }

    }
}
