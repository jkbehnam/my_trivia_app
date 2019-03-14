package com.trivia.trivia.home.Registration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.trivia.trivia.R;
import com.trivia.trivia.base.BaseActivity2;
import com.trivia.trivia.home.gameActivity.dialogTime.Dialog_time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class reg_data extends BaseActivity2 {

    AlertDialog ad;
    private int currentStep = 0;

    @BindView(R.id.et_gender)
    EditText gender;
    String bdate;
    @BindView(R.id.province)
    EditText province;
    @BindView(R.id.et_city)
    EditText et_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        // reg_bdate.setFocusable(false);
        setToolbar("اطلاعات شخصی");
        final StepView stepView = (StepView) findViewById(R.id.step_view);
        stepView.setStepsNumber(2);


        RelativeLayout first_data = (RelativeLayout) findViewById(R.id.RfirstDate);
        RelativeLayout second_data = (RelativeLayout) findViewById(R.id.RsecondData);

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
                currentStep++;
                stepView.go(currentStep, true);
                select_lay(lays, currentStep);

                if ((currentStep == stepView.getStepCount() - 1)) {
                }
            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog(view);
            }
        });
        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProvinceDialog(view);
            }
        });
        et_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityDialog(view);
            }
        });
        findViewById(R.id.et_bdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbdateDialog(view);
            }
        });


    }


    private void showGenderDialog(final View view) {
        ArrayList<String> a = new ArrayList<>();
        final CharSequence[] charSequenceArr = new String[]{"مرد", "زن"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) "جنسیت");
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) view).setText(charSequenceArr[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showCityDialog(final View view) {
        if (province != null&&!province.getText().toString().equals("")) {
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
                if (formula_value.equals(province.getText().toString())) {
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
        builder.setTitle((CharSequence) "شهر های استان "+province.getText().toString());
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) view).setText(charSequenceArr[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }else Toast.makeText(this, "استان را انتخاب نکردید!", Toast.LENGTH_SHORT).show();
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
        builder.setTitle((CharSequence) "استان");
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) view).setText(charSequenceArr[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showbdateDialog(final View view) {
        Dialog_time dt = new Dialog_time();
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
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
