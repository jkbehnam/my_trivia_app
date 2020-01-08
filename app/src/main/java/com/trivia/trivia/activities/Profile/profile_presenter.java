package com.trivia.trivia.activities.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trivia.trivia.webservice.connectToServer.getUserScore;

public class profile_presenter {
    public Fragment_profile fragment_profile;
    public profile_presenter(Fragment_profile fragment_profile){
        this.fragment_profile=fragment_profile;
    }
    public void get_score(String u_id){
        getUserScore(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    fragment_profile.set_score(obj.getString("u_score"));
                    fragment_profile.set_ex_score(obj.getString("u_ex_score"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, u_id);
    }
    public void reciveRequest(String response) throws JSONException {

    }
}
