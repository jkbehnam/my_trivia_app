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
import com.trivia.trivia.home.Events.FragmentEvent;
import com.trivia.trivia.home.Events.FragmentEventPresenter;
import com.trivia.trivia.home.Registration.RegistrationPresenter;
import com.trivia.trivia.home.buyQuestion.BuyQuestionPresenter;
import com.trivia.trivia.home.gameActivity.Fragment_main_event;
import com.trivia.trivia.home.gameActivity.answerQuestion.AnswerQuestionPresenter;
import com.trivia.trivia.home.gameActivity.messages.DetailActivity;
import com.trivia.trivia.home.gameActivity.messages.FragmentNewsPresenter;
import com.trivia.trivia.home.questionList.FragmentQuestionsPresenter;
import com.trivia.trivia.home.solveQuestion.SolvingListFragmentPresenter;
import com.trivia.trivia.home.source.FragmentSourcePresenter;
import com.trivia.trivia.ranking.FragmentRankingPresenter;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Gamer;
import com.trivia.trivia.util.Group;
import com.trivia.trivia.util.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.e;
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
    public static void getAcountBalance(BuyQuestionPresenter buyQuestionPresenter, Group g) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_ACOUNT_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
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
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.g_id());
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

                        final String result = response.toString();
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
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.g_id());
                param.put("e_id", Fragment_main_event.event.getE_id());
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void getMainSetting(FragmentEvent fragmentEvent) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MAIN_SETTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
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

                        Log.d("answer"  ,  response); //when response come i will log it
                        multipleChoicePresenter.answerResponse(response,position,id);

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
                param.put("id", id);
                param.put("q_id", q_id);
                param.put("choose", choose);
                param.put("u_id", u_id);

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

                        Log.d("answer"  ,  response); //when response come i will log it
                        multipleChoicePresenter.shortAnswerResponse(response,id);

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

                        Log.d("score"  ,  response); //when response come i will log it
                        multipleChoicePresenter.setQuestionScore(response);

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
                param.put("id", id);


                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
    }
    public static void buyQuestion(BuyQuestionPresenter buyQuestionPresenter, Event e,Group g,String q_type,String q_level) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RANDOM_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
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
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("g_id", g.g_id());
                param.put("e_id", e.getE_id());
                param.put("q_type", q_type);
                param.put("q_level", q_level);
                return param;
            }
        };


        VolleySingleton.getInstance(homecontext).addToRequestQueue(stringRequest);
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
    public static void getuser_group(FragmentEventPresenter fragmentEventPresenter, String u_id, Event e) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USERGROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it
                        try {
                            fragmentEventPresenter.reciveGroupRequeset(response,e);
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

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it

                        fragmentEventPresenter.reciveRequeset_start_gamer_timer(response,g,e);

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
                param.put("g_id", g.g_id());

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
                        fragmentEventPresenter. gotopayment(u_id,e_id);

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

                param.put("g_id", Fragment_main_event.group.g_id());
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

                        final String result = response.toString();
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

                        final String result = response.toString();
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
    public static void getnewsitem(DetailActivity detailActivity,int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_NEWS_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
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

                        final String result = response.toString();
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

                        final String result = response.toString();
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

                param.put("g_id", Fragment_main_event.group.g_id());
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

                        final String result = response.toString();
                        Log.d("response", "result : " + result); //when response come i will log it

                        if(response.equals("امتیاز اضافه شد")){

                            solvingListFragmentPresenter.refreshEventList_wirhoutserver(selectedItemPositions);
                        }else {

                            solvingListFragmentPresenter.refreshEventList();
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

                param.put("id_list", jArrayActionLog.toString());
                param.put("g_id", Fragment_main_event.group.g_id());
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
    public static void getShortAnswerLenght(AnswerQuestionPresenter multipleChoicePresenter, String q_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_SHORT_ANSWER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final String result = response.toString();
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
