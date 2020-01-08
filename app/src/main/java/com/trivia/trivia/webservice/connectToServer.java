package com.trivia.trivia.webservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trivia.trivia.activities.Events.FragmentEvent;
import com.trivia.trivia.activities.Events.FragmentEventPresenter;
import com.trivia.trivia.activities.Events.IeventAlertview;
import com.trivia.trivia.activities.Game.GameSellQuestion.SellQuestion.FragmentSellQuestionsPresenter;
import com.trivia.trivia.activities.LoginRegisteration.Registration.RegistrationPresenter;
import com.trivia.trivia.activities.Game.GameBuyQuestion.BuyQuestionPresenter;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.AnswerQuestionPresenter;
import com.trivia.trivia.activities.Messages.DetailActivity;
import com.trivia.trivia.activities.Messages.FragmentNewsPresenter;
import com.trivia.trivia.activities.Game.GameGamerQuestion.FragmentQuestionsPresenter;
import com.trivia.trivia.activities.Game.GameSolveQuestion.SolvingListFragmentPresenter;
import com.trivia.trivia.activities.Game.GameGuideSource.FragmentSourcePresenter;
import com.trivia.trivia.activities.Game.GameRanking.FragmentRankingPresenter;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.activities.Profile.profile_presenter;
import com.trivia.trivia.activities.Profile.reg_data;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Gamer;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.util.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.trivia.trivia.activities.HomeBase.Home.homecontext;
import static com.trivia.trivia.activities.LoginRegisteration.login.LoginActivity.maincontext;

public class connectToServer {
    public static void sendSms(RegistrationPresenter registrationPresenter, String phone) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_REQUEST_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                registrationPresenter.smsRequestRecived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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

    public static void getAcountBalance(BuyQuestionPresenter buyQuestionPresenter, Group g) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_ACOUNT_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            buyQuestionPresenter.setBalance(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.getG_id());
                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getAcountBalance(Fragment_main_event fragment_main_event, Group g) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_ACOUNT_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        fragment_main_event.setBalance(response);


                        JSONObject userJson = null;
                        try {
                            JSONObject obj = new JSONObject(response);
                            userJson = obj.getJSONObject("score");
                            fragment_main_event.setBalance(userJson.getString("g_score"));
                            fragment_main_event.setInflation(userJson.getString("inflation"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.getG_id());
                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getMainImg(FragmentEvent fragmentEvent) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MAIN_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEvent.set_main_img(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("app_name", "arithland");
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getMainSetting(VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MAIN_SETTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {

                            JSONObject obj = new JSONObject(response);
                            PrefManager pm;
                            String s =obj.getString("prof_toman");
                            pm = new PrefManager(homecontext);
                            pm.set_prof_toman(s);
                            callback.onSuccess(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("app_name", "arithland");
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void answerMultichoice(AnswerQuestionPresenter multipleChoicePresenter, String choose, String id, String q_id, String u_id, int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ANSWER_MULTICHOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("answer", response); //when response come i will log it
                        multipleChoicePresenter.answerResponse(response, position, id);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id);
                param.put("q_id", q_id);
                param.put("choose", choose);
                param.put("u_id", u_id);
                param.put("g_id",Fragment_main_event.group.getG_id() );
                param.put("e_id",Fragment_main_event.event.getE_id() );
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void answerShortAnswer(AnswerQuestionPresenter multipleChoicePresenter, String choose, String id, String q_id, String u_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ANSWER_MULTICHOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("answer", response); //when response come i will log it
                        multipleChoicePresenter.shortAnswerResponse(response, id);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id);
                param.put("q_id", q_id);
                param.put("choose", choose);
                param.put("u_id", u_id);
                param.put("g_id",Fragment_main_event.group.getG_id() );
                param.put("e_id",Fragment_main_event.event.getE_id() );
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void answerDescAnswer(AnswerQuestionPresenter multipleChoicePresenter, String choose, String id, String q_id, String u_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ANSWER_MULTICHOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("answer", response); //when response come i will log it
                        multipleChoicePresenter.shortAnswerResponse(response, id);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id);
                param.put("q_id", q_id);
                param.put("choose", choose);
                param.put("u_id", u_id);

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getQuestionScore(AnswerQuestionPresenter multipleChoicePresenter, String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_QUESTION_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("score", response); //when response come i will log it
                        multipleChoicePresenter.setQuestionScore(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id);


                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void buyQuestion(BuyQuestionPresenter buyQuestionPresenter, Event e, Group g, String q_type, String q_level) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RANDOM_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            buyQuestionPresenter.reciveRequeset(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.getG_id());
                param.put("e_id", e.getE_id());
                param.put("q_type", q_type);
                param.put("q_level", q_level);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void sendOtp(RegistrationPresenter registrationPresenter, String code, String phone) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLs.URL_VERIFY_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                registrationPresenter.optRequestRecived(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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

    public static void sendGamerData(Gamer gamer, Context maincontext) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REG_GAMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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
                            if (error) checkUserName.exist();
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
                        show_error_warning(error, homecontext);
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

    public static void getEventsList(FragmentEventPresenter fragmentEventPresenter, String u_id, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EVENT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveRequeset(response, page);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("u_id", u_id);
                param.put("u_page", String.valueOf(page));
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getEventsList(FragmentEventPresenter fragmentEventPresenter, String u_id, int page,String search) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EVENT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveRequeset(response, page);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("u_id", u_id);
                param.put("u_page", String.valueOf(page));
                param.put("search", search);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getEventsList_reg(FragmentEventPresenter fragmentEventPresenter, String u_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EVENT_LIST_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveRequeset(response, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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

    public static void getuser_group(FragmentEventPresenter fragmentEventPresenter, String u_id, Event e) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USERGROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveGroupRequeset(response, e);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("u_id", u_id);
                param.put("e_id", e.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void start_game_timer(FragmentEventPresenter fragmentEventPresenter, Group g, Event e) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_START_GAME_TIMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it

                        fragmentEventPresenter.reciveRequeset_start_gamer_timer(response, g, e);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.getG_id());

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void regUserEvent(IeventAlertview event_dialog, String u_id, String e_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GAMER_EVENT_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        event_dialog.showPaydialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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

    public static void getQuestionsList(FragmentQuestionsPresenter fragmentQestionsPresenter, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_QUESTIONS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
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
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                String s1=Fragment_main_event.group.getG_id();
                param.put("g_id", s1);
                param.put("g_page", String.valueOf(page));

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getSellQuestionsList(FragmentSellQuestionsPresenter fragmentSellQuestionsPresenter, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_Get_SELL_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentSellQuestionsPresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                String s1=Fragment_main_event.group.getG_id();
                param.put("g_id", s1);
                param.put("e_id",Fragment_main_event.event.getE_id() );
                param.put("g_page", String.valueOf(page));

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getMySellQuestionsList(FragmentSellQuestionsPresenter fragmentSellQuestionsPresenter, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_Get_MY_SELL_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentSellQuestionsPresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                String s1=Fragment_main_event.group.getG_id();
                param.put("g_id", s1);
                param.put("e_id",Fragment_main_event.event.getE_id() );
                param.put("g_page", String.valueOf(page));

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void sellQuestion(FragmentQuestionsPresenter fragmentQestionsPresenter,String id, String q_id, String price,int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SELL_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        fragmentQestionsPresenter.sellRequeset(response,position);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("id",id );
                param.put("g_id",Fragment_main_event.group.getG_id() );
                param.put("q_id",q_id );
                param.put("e_id",Fragment_main_event.event.getE_id() );
                param.put("price",price );


                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void sellExchangeQuestion(FragmentSellQuestionsPresenter fragmentSellQuestionsPresenter,String id,int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SELL_EXCHANGE_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        fragmentSellQuestionsPresenter.sellExchangeRequeset(response,position);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("id",id );
                param.put("g_id",Fragment_main_event.group.getG_id() );




                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getSourceList(FragmentSourcePresenter fragmentSourcePresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_event_source,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentSourcePresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getnewsList(FragmentNewsPresenter fragmentNewsPresenterPresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_NEWS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentNewsPresenterPresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("app_name", "arithland");
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getnewsitem(DetailActivity detailActivity, int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_NEWS_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            detailActivity.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("news_id", String.valueOf(id));
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getRankingList(FragmentRankingPresenter fragmentRankingPresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RANKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentRankingPresenter.reciveRequeset(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getSolvedQuestionsList(SolvingListFragmentPresenter solvingListFragmentPresenter) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_SOLVED_QUESTIONS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            solvingListFragmentPresenter.reciveRequeset(response);
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

                param.put("g_id", Fragment_main_event.group.getG_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void SetUserDetails(reg_data reg_data,UserDetail userDetail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SET_USER_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        //reg_data.reciveRequeset(response);
                        if(response.equals("ok")){

                            reg_data.save_data(userDetail);
                        }
                        //Toast.makeText(reg_data, response, Toast.LENGTH_SHORT).show();

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

                param.put("u_id", userDetail.getU_id());
                param.put("ud_name", userDetail.getUd_name());
                param.put("ud_email", userDetail.getUd_email());
                param.put("ud_birthday", userDetail.getUd_birthday());
                param.put("ud_sex", userDetail.getUd_sex());
                param.put("ud_national_code", userDetail.getUd_national_code());
                param.put("ud_country", userDetail.getUd_country());
                param.put("ud_city", userDetail.getUd_city());
                param.put("ud_grade", userDetail.getUd_grade());
                param.put("ud_school", userDetail.getUd_school());
                param.put("ud_province", userDetail.getUd_province());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getUserScore(VolleyCallback callback, String u_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_USER_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //reg_data.reciveRequeset(response);



                          ///  profile_presenter.reciveRequest(response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(reg_data, response, Toast.LENGTH_SHORT).show();

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

    public static void getChats(VolleyCallback callback, Map<String, String> param) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_CHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //reg_data.reciveRequeset(response);

                        ///  profile_presenter.reciveRequest(response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(reg_data, response, Toast.LENGTH_SHORT).show();

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
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void any_send(VolleyCallback callback, Map<String, String> param,String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //reg_data.reciveRequeset(response);

                        ///  profile_presenter.reciveRequest(response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(reg_data, response, Toast.LENGTH_SHORT).show();

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
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void reg_gamer_event_score(VolleyCallback callback, String u_id,String e_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REG_GAMER_EVENT_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //reg_data.reciveRequeset(response);



                        ///  profile_presenter.reciveRequest(response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(reg_data, response, Toast.LENGTH_SHORT).show();

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
                param.put("e_id", e_id);
                param.put("app_name", "arithland");

                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void setSolvedQuestion(SolvingListFragmentPresenter solvingListFragmentPresenter, JSONArray jArrayActionLog, List<Integer> selectedItemPositions) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SET_SOLVED_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it

                        if (response.equals("امتیاز اضافه شد")) {

                            solvingListFragmentPresenter.refreshEventList_wirhoutserver(selectedItemPositions);
                        } else {

                            solvingListFragmentPresenter.refreshEventList();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
String i=Fragment_main_event.group.getG_id();
                String j=Fragment_main_event.event.getE_id();
                param.put("id_list", jArrayActionLog.toString());
                param.put("g_id", Fragment_main_event.group.getG_id());
                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }

    public static void getMultiplechoiceQuestion(AnswerQuestionPresenter multipleChoicePresenter, String q_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_MULTIPLECHOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
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
                        show_error_warning(error, homecontext);
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

    public static void getShortAnswerLenght(AnswerQuestionPresenter multipleChoicePresenter, String q_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_SHORT_ANSWER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response;
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            multipleChoicePresenter.reciveSLenght(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, homecontext);
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
    public static void show_error_warning(VolleyError error, Context context) {

        String message = null;
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }


        //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
