package com.trivia.trivia.webservice;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.home.RegistrationPresenter;
import com.trivia.trivia.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.trivia.trivia.login.LoginActivity.maincontext;

public class connectToServer {

   public static void sendSms(RegistrationPresenter registrationPresenter, String phone) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_REQUEST_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                registrationPresenter.smsRequestRecived(response);
                //     JSONObject responseObj = new JSONObject(response);

                // Parsing json object response
                // response will be a json object
                //      boolean error = responseObj.getBoolean("error");
                //     String message = responseObj.getString("message");

                // checking for error, if not error SMS is initiated
                // device should receive it shortly
                //  if (!error) {
                // boolean flag saying device is waiting for sms


                // moving the screen to next pager item i.e otp screen

                //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                //  } else {
                //      Toast.makeText(getApplicationContext(),
                //              "Error1: " + message,
                //               Toast.LENGTH_LONG).show();
                //   }

                // hiding the progress bar


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "");
                params.put("email", "");
                params.put("mobile", phone);

                Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }

        };

        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);

        // Adding request to request queue
        VolleySingleton.getInstance(maincontext).addToRequestQueue(strReq);
    }

    public static void sendOtp(RegistrationPresenter registrationPresenter,String code){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_VERIFY_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
               registrationPresenter.optRequestRecived(response);
/*
                try {

                   JSONObject responseObj = new JSONObject(response);

                    // Parsing json object response
                    // response will be a json object
                    boolean error = responseObj.getBoolean("error");
                    String message = responseObj.getString("message");

                    if (!error) {
                        // parsing the user profile information
                        JSONObject profileObj = responseObj.getJSONObject("profile");

                        String name = profileObj.getString("name");
                        String email = profileObj.getString("email");
                        String mobile = profileObj.getString("mobile");

                        PrefManager pref = new PrefManager(getApplicationContext());
                        pref.createLogin(name, email, mobile);

                        Intent intent = new Intent(HttpService.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            //    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", code);

                Log.e(TAG, "Posting params: " + params.toString());
                return params;
            }

        };

        // Adding request to request queue
        VolleySingleton.getInstance(maincontext).addToRequestQueue(strReq);
    }
}
