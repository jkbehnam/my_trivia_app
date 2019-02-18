package com.trivia.trivia.webservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.home.Events.FragmentEventPresenter;
import com.trivia.trivia.home.Registration.RegistrationPresenter;
import com.trivia.trivia.home.gameActivity.MultipleChoicePresenter;
import com.trivia.trivia.home.questionList.FragmentQuestionsPresenter;
import com.trivia.trivia.util.Gamer;
import com.trivia.trivia.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.trivia.trivia.home.HomeBase.Home.homecontext;
import static com.trivia.trivia.login.LoginActivity.maincontext;

public class connectToServer {
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

    public static void sendOtp(RegistrationPresenter registrationPresenter,String code,String phone){
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
    public static void sendGamerData(Gamer gamer,Context maincontext) {


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
                param.put("username", gamer.getName()); // array is key which we will use on server side
                param.put("password", gamer.getPassword());
                param.put("phonenum", gamer.getPhone_number());

                return param;
            }
        };


        VolleySingleton.getInstance(maincontext).addToRequestQueue(stringRequest);
    }
    public static void check_username(String username, Context maincontext, RegistrationPresenter.checkUserName checkUserName) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CHECK_USERNAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject responseObj = new JSONObject(response);
                            boolean error = responseObj.getBoolean("message");
                            if(error) checkUserName.exist();
                            else checkUserName.notexist();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(maincontext, "wrong", Toast.LENGTH_SHORT).show();
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
                param.put("username", username); // array is key which we will use on server side
                return param;
            }
        };


        VolleySingleton.getInstance(maincontext).addToRequestQueue(stringRequest);

    }
    public static void getEventsList(FragmentEventPresenter fragmentEventPresenter,String u_id) {
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
                param.put("u_id", u_id);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getEventsList_reg(FragmentEventPresenter fragmentEventPresenter,String u_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EVENT_LIST_REG,
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
                param.put("u_id", u_id);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void regUserEvent(FragmentEventPresenter fragmentEventPresenter,String u_id,String e_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GAMER_EVENT_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it
                       fragmentEventPresenter.refreshEventList(u_id);
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
                param.put("e_id", e_id);
                param.put("u_id", u_id);

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getQuestionsList(FragmentQuestionsPresenter fragmentQestionsPresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_QUESTIONS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentQestionsPresenter.reciveRequeset(response);
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
                param.put("u_id", "45");
                param.put("e_id", "1");
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getMultiplechoiceQuestion(MultipleChoicePresenter multipleChoicePresenter,String q_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_MULTIPLECHOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            multipleChoicePresenter.reciveRequeset(response);
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
                param.put("q_id", q_id);

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

}
