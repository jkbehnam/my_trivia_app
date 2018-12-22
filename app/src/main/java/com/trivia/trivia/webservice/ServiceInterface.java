package com.trivia.trivia.webservice;

import com.trivia.trivia.webservice.model.request.Login;
import com.trivia.trivia.webservice.model.response.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * This interface will describe service methods.
 */

public interface ServiceInterface {

    static final String LOGIN = "callLogin";

    @POST(LOGIN)
    Call<ResponseLogin> login(@Body Login loginCall);

}
