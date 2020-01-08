package com.trivia.trivia.activities.Profile;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shuhart.stepview.StepView;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Profile.dialogTime.DialogTime;
import com.trivia.trivia.base.BaseActivity;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.UserDetail;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class reg_data extends BaseActivity {
    PrefManager pm;
    AlertDialog ad;
    private int currentStep = 0;


    @BindView(R.id.ud_name)
    EditText ud_name;
    @BindView(R.id.ud_email)
    EditText ud_email;
    @BindView(R.id.ud_birthday)
    EditText ud_birthday;
    @BindView(R.id.ud_sex)
    EditText ud_sex;
    @BindView(R.id.ud_national_code)
    EditText ud_national_code;
    @BindView(R.id.ud_country)
    EditText ud_country;
    @BindView(R.id.ud_city)
    EditText ud_city;
    @BindView(R.id.ud_grade)
    EditText ud_grade;
    @BindView(R.id.ud_school)
    EditText ud_school;
    @BindView(R.id.ud_province)
    EditText ud_province;

    reg_data reg_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        // reg_bdate.setFocusable(false);
        setToolbar("اطلاعات شخصی");
        final StepView stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(2);
        reg_data = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }

        RelativeLayout first_data = findViewById(R.id.RfirstDate);
        RelativeLayout second_data = findViewById(R.id.RsecondData);

        List<RelativeLayout> lays = new ArrayList<>();

        lays.add(first_data);
        lays.add(second_data);

        select_lay(lays, 0);


        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                select_lay(lays, 1 - step);
                stepView.go(1 - step, true);
                currentStep = 1 - step;

            }
        });

        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<EditText> editTexts = new ArrayList<>();


                editTexts.add(ud_name);
                editTexts.add(ud_national_code);
                editTexts.add(ud_email);
                editTexts.add(ud_birthday);
                editTexts.add(ud_sex);
                if (check_item(editTexts)) {
                currentStep++;
                stepView.go(currentStep, true);
                select_lay(lays, currentStep);

                if ((currentStep == stepView.getStepCount() - 1)) {
                }
            }}
        });

        findViewById(R.id.bt_submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<EditText> editTexts = new ArrayList<>();
                editTexts.add(ud_name);
                editTexts.add(ud_national_code);
                editTexts.add(ud_email);
                editTexts.add(ud_birthday);
                editTexts.add(ud_sex);
                editTexts.add(ud_country);
                editTexts.add(ud_city);
                editTexts.add(ud_grade);
                editTexts.add(ud_school);
                editTexts.add(ud_province);




                if (check_item(editTexts)) {
                    UserDetail userDetail = new UserDetail();
                    pm = new PrefManager(getApplicationContext());
                    userDetail.setU_id(pm.getUserDetails().get("u_id"));
                    userDetail.setUd_name(ud_name.getText().toString());
                    userDetail.setUd_email(ud_email.getText().toString());
                    userDetail.setUd_birthday(ud_birthday.getText().toString());
                    userDetail.setUd_sex(ud_sex.getText().toString());
                    userDetail.setUd_national_code(ud_national_code.getText().toString());
                    userDetail.setUd_country(ud_country.getText().toString());
                    userDetail.setUd_city(ud_city.getText().toString());
                    userDetail.setUd_grade(ud_grade.getText().toString());
                    userDetail.setUd_school(ud_school.getText().toString());
                    userDetail.setUd_province(ud_province.getText().toString());

                    connectToServer.SetUserDetails(reg_data, userDetail);

                }
            }
        });

        ud_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog(view);
            }
        });
        ud_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProvinceDialog(view);
            }
        });
        ud_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityDialog(view);
            }
        });
        ud_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbdateDialog(view);
            }
        });
        show_data();

    }


    private void showGenderDialog(final View view) {
        ArrayList<String> a = new ArrayList<>();
        final CharSequence[] charSequenceArr = new String[]{"مرد", "زن"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("جنسیت");
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) view).setText(charSequenceArr[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showCityDialog(final View view) {
        if (ud_province != null && !ud_province.getText().toString().equals("")) {
            ArrayList<String> s = new ArrayList<>();
            boolean found = false;
            try {
                //JSONObject obj = new JSONObject(loadJSONFromAsset());
                //  JSONArray m_jArry = obj.getJSONArray(loadJSONFromAsset());
                JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
                ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m_li;

                for (int i = 0; i < m_jArry.length() && !found; i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    String formula_value = jo_inside.getString("name");
                    if (formula_value.equals(ud_province.getText().toString())) {
                        JSONArray cities = jo_inside.getJSONArray("Cities");
                        for (int i2 = 0; i2 < cities.length(); i2++) {
                            JSONObject city = cities.getJSONObject(i2);
                            // Log.d("Details-->", jo_inside.getString("formule"));
                            String cityname = city.getString("name");
                            s.add(cityname);
                            found = true;
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final CharSequence[] charSequenceArr = s.toArray(new CharSequence[0]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("شهر های استان " + ud_province.getText().toString());
            builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ((EditText) view).setText(charSequenceArr[i]);
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else Toast.makeText(this, "استان را انتخاب نکردید!", Toast.LENGTH_SHORT).show();
    }

    private void showProvinceDialog(final View view) {
        ArrayList<String> s = new ArrayList<>();
        try {
            //JSONObject obj = new JSONObject(loadJSONFromAsset());
            //  JSONArray m_jArry = obj.getJSONArray(loadJSONFromAsset());
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                // Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("name");
                // String url_value = jo_inside.getString("url");
                s.add(formula_value);
                //Add your values in your `ArrayList` as below:
                // m_li = new HashMap<String, String>();
                // m_li.put("formule", formula_value);
                // m_li.put("url", url_value);

                //formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final CharSequence[] charSequenceArr = s.toArray(new CharSequence[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("استان");
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) view).setText(charSequenceArr[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showbdateDialog(final View view) {
        DialogTime dt = new DialogTime();
        ad = dt.qrcode_reader(reg_data.this, view);
        ad.show();

    }


    public void select_lay(List<RelativeLayout> lays, int currentStep) {
        for (RelativeLayout lay : lays
        ) {
            lay.setVisibility(View.GONE);

        }
        lays.get(currentStep).setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayout() {
        return R.layout.reg_form;
    }

    @Override
    public void init() {

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("Province.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public Boolean check_item(ArrayList<EditText> editTexts) {
        Boolean check = true;
        for (EditText e : editTexts
        ) {
            String chara = e.getText().toString();
            if (TextUtils.isEmpty(chara)) {
                repetitiousUsername(e);
                e.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        e.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                check = false;

            }

        }
        return check;
    }


    public void repetitiousUsername(EditText editText) {
        Drawable myIcon = getResources().getDrawable(R.drawable.cancel);
        myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
        editText.setError("لطفا این فیلد را کامل کنید.", myIcon);

    }

    public void save_data(UserDetail userDetail) {
        String s = new Gson().toJson(userDetail);
         pm = new PrefManager(this);
        pm.user_details(s);
        this.finish();
    }

    public void show_data()  {

        try {
            pm = new PrefManager(getApplicationContext());

            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            String sd=pm.get_user().get("user_detail");
            // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
            JSONObject obj = new JSONObject(pm.get_user().get("user_detail"));
            final UserDetail userDetails = gson.fromJson(obj.toString(), UserDetail.class);



            ud_name.setText(userDetails.getUd_name());
            ud_email.setText(userDetails.getUd_email());
            ud_birthday.setText(userDetails.getUd_birthday());
            ud_sex.setText(userDetails.getUd_sex());
            ud_national_code.setText(userDetails.getUd_national_code());
            ud_country.setText(userDetails.getUd_country());
            ud_city.setText(userDetails.getUd_city());
            ud_grade.setText(userDetails.getUd_grade());
            ud_school.setText(userDetails.getUd_school());
            ud_province.setText(userDetails.getUd_province());

        }catch (Exception e){}


    }
}
