
package com.trivia.trivia.home.gameActivity.FragmentChat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;

import java.util.ArrayList;
import java.util.Calendar;


import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class FragmentGroupMessage extends myFragment {
    ArrayList<ChatMessage> chat_list;
    FragmentActivity c;
    static FragmentGroupMessage fh;
    ChatView chatView;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_group_message, container, false);
        setRetainInstance(true);
        c = getActivity();
        fh = this;

        chatView = (ChatView) rootView.findViewById(R.id.chat_view);
chatView.addMessage(new ChatMessage("سلام",1548570748, ChatMessage.Type.SENT,"alii"));
        chatView.addMessage(new ChatMessage("سلام",1548550748, ChatMessage.Type.RECEIVED,"ahmad"));
       // show_messages();

      //  update_server_data.GetChatMessages(this,MainActivity.mainContext);
       // update_server_data.setread_message(MainActivity.mainContext);

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {

           //     DataBase_write.write_chat_sender(chatMessage.getMessage());
              //  update_server_data.send_chat_array(MainActivity.mainContext);

                return true;
            }
        });
setToolbar(rootView,"چت گروهی");

        return rootView;
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public void show_messages() {
        chat_list = new ArrayList<>();
       /* ArrayList<myChatMessage> list = DataBase_read.get_chats(MainActivity.mainContext);
        for (myChatMessage chat : list
                ) {
            if (chat.getWho_send().equals("patient")) {
                Long d = Long.parseLong(chat.getTimestamp());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(d * 1000);
                String s = cal.getTime().toString();
                chat_list.add(new ChatMessage(chat.getMessage(), Long.parseLong(chat.getTimestamp()) * 1000, ChatMessage.Type.SENT));

            } else {
                chat_list.add(new ChatMessage(chat.getMessage(), Long.parseLong(chat.getTimestamp()) * 1000, ChatMessage.Type.RECEIVED));
            }
        }
*/
        chatView.clearMessages();
        chatView.addMessages(chat_list);
    }
}