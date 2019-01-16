package com.trivia.trivia.webservice;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.home.Events.FragmentEventPresenter;
import com.trivia.trivia.home.Registration.RegistrationPresenter;
import com.trivia.trivia.util.Gamer;
import com.trivia.trivia.util.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.trivia.trivia.home.HomeBase.Home.homecontext;
import static com.trivia.trivia.login.LoginActivity.maincontext;

public class connectToServer {
    public static void get_questions(final Context context, final String id, final Object a) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_QUESTIONS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {

                            //converting response to json object

                            String p_name = "";
                            String p_score = "";
                            int answerd_questions = 0;
                           // DataBase.getInstance(context).getDb().execSQL("delete from " + "Question");

                            JSONArray us = new JSONArray(response);
                            for (int i = 0; i < us.length(); i++) {
                                JSONObject e = us.getJSONObject(i);


                            }

                            //Toast.makeText(context, String.valueOf(q_list.size()), Toast.LENGTH_SHORT).show();


                            //if no error in response

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p_Code", id);
              //  params.put("c_Code", comp.getC_code());

                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }
   public static void sendSms(RegistrationPresenter registrationPresenter, String phone) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_REQUEST_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                registrationPresenter.smsRequestRecived(response);
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
    public static void sendGamerData(JSONArray jArrayActionLog,Context maincontext) {

        final String newDataArray = jArrayActionLog.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REG_GAMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("array", newDataArray); // array is key which we will use on server side
                return param;
            }
        };


        VolleySingleton.getInstance(maincontext).addToRequestQueue(stringRequest);
    }
    public static void getEventsList(FragmentEventPresenter fragmentEventPresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EVENT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static JSONArray createjArrayGamer(Gamer gamer) {
        JSONArray jArrayActionLog = new JSONArray();
        //Log.e(LOG, selectQuery);
        //  ArrayList<Gamer> g = DataBase_read.give_gamers_list(context);


            try {
                JSONObject jObjectPatientQuestion = new JSONObject();
                jObjectPatientQuestion.put("pname", String.valueOf(gamer.getName()));
                jObjectPatientQuestion.put("pphonenum", String.valueOf(gamer.getPhone_number()));
                jObjectPatientQuestion.put("ppassword", String.valueOf(gamer.getPassword()));

                jArrayActionLog.put(jObjectPatientQuestion);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        return jArrayActionLog;
    }
}
