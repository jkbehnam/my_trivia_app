package com.trivia.trivia.webservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseLogin {

    @SerializedName("Name")
    @Expose
    private String username;

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("u_id")
    @Expose
    private String u_id;


    /**
     *
     * @return
     * The id
     */


    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    /**
     *
     * @return
     * The email
     */


}
