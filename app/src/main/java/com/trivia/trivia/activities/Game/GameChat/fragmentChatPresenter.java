package com.trivia.trivia.activities.Game.GameChat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.util.ObjectBox;
import com.trivia.trivia.util.chatMessage;
import com.trivia.trivia.util.chatMessage_;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;

public class fragmentChatPresenter {
    Box<chatMessage> chatMessageBox;
    FragmentGroupMessage fragmentGroupMessage;

    fragmentChatPresenter(FragmentGroupMessage fragmentGroupMessage) {
        this.fragmentGroupMessage = fragmentGroupMessage;
        chatMessageBox = ObjectBox.get().boxFor(chatMessage.class);
    }

    public void get_chat(int id) {

        Map<String, String> param = new HashMap<String, String>();

        param.put("e_id", Fragment_main_event.event.getE_id());
        param.put("g_id", Fragment_main_event.group.getG_id());
        param.put("id", String.valueOf(id));
        connectToServer.getChats(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
                    JSONObject obj = new JSONObject(result);
                    ArrayList<chatMessage> chatMessages = new ArrayList<>();
                    final chatMessage[] chatMessages1 = gson.fromJson(obj.getString("chat"), chatMessage[].class);
                    chatMessages.addAll(Arrays.asList(chatMessages1));
                    save_chat(chatMessages);
                    fragmentGroupMessage.addtochat(chatMessages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, param);
    }

    public void show_chat() {

        ArrayList<chatMessage> chatMessages = new ArrayList<>();
       // List<chatMessage> chatMessageList = chatMessageBox.getAll();
        List<chatMessage> chatMessageList = chatMessageBox.query().equal(chatMessage_.e_id,Fragment_main_event.event.getE_id() ).build().find();

        chatMessages.addAll(chatMessageList);
        if (chatMessageList.size() > 0 ) {
            fragmentGroupMessage.addtochat(chatMessages);
            get_chat(chatMessageList.get(chatMessageList.size() - 1).getId());
        } else {
            fragmentGroupMessage.addtochat(chatMessages);
            get_chat(0);
        }


    }

    public void save_chat(ArrayList<chatMessage> chatMessages) {

        for (chatMessage cm : chatMessages
        ) {
            chatMessageBox.put(cm);
        }

    }

}
