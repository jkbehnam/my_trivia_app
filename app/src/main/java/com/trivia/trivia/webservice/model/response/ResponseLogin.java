package com.trivia.trivia.webservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseLogin {

    @SerializedName("Name")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String nickname;



    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @param nickname
     * The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return
     * The email
     */


}
