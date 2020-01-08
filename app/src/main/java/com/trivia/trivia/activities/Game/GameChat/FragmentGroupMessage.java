package com.trivia.trivia.activities.Game.GameChat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.custom_widgets.ChatView;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.chatMessage;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.models.ChatMessage;

import static com.trivia.trivia.activities.Game.GameChat.SocketIOService.EVENT_TYPE_MESSAGE;


public class FragmentGroupMessage extends BaseFragment {
    //public static String chatURL = "http://172.24.34.89:3000/";
    public static String chatURL = "https://my-game-behnam.liara.run/";
    static FragmentGroupMessage fh;
    ArrayList<ChatMessage> chat_list;
    FragmentActivity c;
    ChatView chatView;
    View rootView;
    @BindView(R.id.name_toolbar)
    TextView name_toolbar;
    fragmentChatPresenter fragmentChatPresenter;
    //private Socket socket;
    private String Nickname;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_group_message, container, false);
        ButterKnife.bind(this, rootView);
        name_toolbar.setText("چت گروهی");
        //  setRetainInstance(true);
        c = getActivity();
        fh = this;
        fragmentChatPresenter = new fragmentChatPresenter(this);

        chatView = rootView.findViewById(R.id.chat_view);
//chatView.addMessage(new ChatMessage("سلام",1548570748, ChatMessage.Type.SENT,"alii"));
        //    chatView.addMessage(new ChatMessage("سلام",1548550748, ChatMessage.Type.RECEIVED,"ahmad"));
        // show_messages();
        PrefManager pm = new PrefManager(getActivity());
        pm.getUserDetails().get("name");
        Nickname = pm.getUserDetails().get("name");
        chatView.clearMessages();
      /*  try {
            socket = IO.socket(chatURL);
           // socket = IO.socket("https://my-game-behnam.liara.run");
            //برقراری ارتباط
            Toast.makeText(c, String.valueOf(Fragment_main_event.group.getG_id()), Toast.LENGTH_SHORT).show();
            socket.connect();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("msg" , String.valueOf(Fragment_main_event.group.getG_id()));
                jsonObject.put("group" , String.valueOf(Fragment_main_event.group.getG_id()));



                socket.emit("join",jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

          //  socket.emit("join",Nickname);

        } catch (URISyntaxException e) {

            e.printStackTrace();

        }
        */


        //  update_server_data.GetChatMessages(this,MainActivity.mainContext);
        // update_server_data.setread_message(MainActivity.mainContext);


        chatView.setOnSentMessageListener(new co.intentservice.chatui.ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                Intent service = new Intent(getContext(), SocketIOService.class);
                service.putExtra(SocketIOService.EXTRA_EVENT_TYPE, EVENT_TYPE_MESSAGE);
                service.putExtra(SocketIOService.EXTRA_DATA, chatMessage.getMessage());
                service.putExtra(SocketIOService.EXTRA_EVENT_ID, Fragment_main_event.event.getE_id());
                service.putExtra(SocketIOService.EXTRA_EVENT_NAME, Fragment_main_event.event.getE_name());
                service.putExtra(SocketIOService.EXTRA_GROUP_ID, Fragment_main_event.group.getG_id());
                service.putExtra(SocketIOService.EXTRA_USER_NAME, Nickname);
                getActivity().startService(service);
                return false;
            }
        });

//setToolbar(rootView,"چت گروهی");

/*
        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            String nickname1 = data.getString("senderNickname");
                            String message = data.getString("message");
                            Calendar c=Calendar.getInstance();
                            if(!Nickname.equals(nickname1)) {
                                chatView.addMessage(new ChatMessage(message, c.getTimeInMillis(), ChatMessage.Type.RECEIVED, nickname1));
                            }else
                            {
                                chatView.addMessage(new ChatMessage(message, c.getTimeInMillis(), ChatMessage.Type.SENT, Nickname));

                            }
                            // make instance of message

                          //  Message m = new Message(nickname,message);


                            //add the message to the messageList

                           // MessageList.add(m);

                            // add the new updated list to the dapter
                          //  chatBoxAdapter = new ChatBoxAdapter(MessageList);

                            // notify the adapter to update the recycler view

                          //  chatBoxAdapter.notifyDataSetChanged();

                            //set the adapter for the recycler view

                       //     myRecylerView.setAdapter(chatBoxAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });

        */

        fragmentChatPresenter.show_chat();
        return rootView;
    }

    public void addtochat(ArrayList<chatMessage> chatMessages) {


        {
            //extract data from fired event

            for (chatMessage cm : chatMessages
            ) {
                if (!Nickname.equals(cm.getUsername())) {
                    chatView.addMessage(new ChatMessage(cm.getContent(), Long.parseLong(cm.getTimestamp()) * 1000, ChatMessage.Type.RECEIVED, cm.getUsername()));
                } else {
                    chatView.addMessage(new ChatMessage(cm.getContent(), Long.parseLong(cm.getTimestamp()) * 1000, ChatMessage.Type.SENT, Nickname));


                }
            }


        }


    }

    public void addtochat(String nickname1, String message) {


        {
            //extract data from fired event


            Calendar c = Calendar.getInstance();
            if (!Nickname.equals(nickname1)) {
                chatView.addMessage(new ChatMessage(message, c.getTimeInMillis(), ChatMessage.Type.RECEIVED, nickname1));
            } else {
                chatView.addMessage(new ChatMessage(message, c.getTimeInMillis(), ChatMessage.Type.SENT, Nickname));

            }

        }


    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }


}