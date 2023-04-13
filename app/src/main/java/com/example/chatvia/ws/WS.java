package com.example.chatvia.ws;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.chatvia.adapter.ContactAdapter;
import com.example.chatvia.config.Config;
import com.example.chatvia.config.Store;
import com.example.chatvia.eventbus.EventActiveChat;
import com.example.chatvia.models.ActiveChat;
import com.example.chatvia.models.BusContact;
import com.example.chatvia.models.BusConversation;
import com.example.chatvia.models.Conversation;
import com.example.chatvia.models.MessageGroupDay;
import com.example.chatvia.ultis.MessageUlti;
import com.example.chatvia.ws.receiver.EventOnGetConversations;
import com.example.chatvia.ws.receiver.EventOnGetFriends;
import com.example.chatvia.ws.receiver.EventOnNewMessage;
import com.example.chatvia.ws.receiver.EventOnStartChatMulti;
import com.example.chatvia.ws.receiver.EventOnStartChatPrivate;
import com.example.chatvia.ws.receiver.Message;
import com.example.chatvia.ws.receiver.WSEventReceiver;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WS {
    private static WS instance;
    private static String id;
    private WebSocket webSocket;
    private OkHttpClient client;

    public WS(String id) {
        Gson gson = new Gson();
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Config.WS_URL + "?id=" + id)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                JsonObject jsonGetOnline = new JsonObject();

                jsonGetOnline.addProperty("command", Command.SEND_GET_ONLINE);
                jsonGetOnline.addProperty("id", id);

                webSocket.send(jsonGetOnline.toString());
                Log.i("WS", "onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.i("WS", "onMessage" + text);
                WSEventReceiver res = gson.fromJson(text, WSEventReceiver.class);

                switch (res.getEvent()) {
                    case Command.LISTEN_GET_FRIENDS:
                    case Command.LISTEN_GET_ONLINE: {
                        EventOnGetFriends data = gson.fromJson(text, EventOnGetFriends.class);
                        Store.friends = data.getFriends();

                        BusContact busContact = new BusContact();
                        busContact.setUsers(data.getFriends());
                        EventBus.getDefault().post(busContact);
                        break;
                    }
                    case Command.LISTEN_NEW_MESSAGE: {
                        EventOnNewMessage data = gson.fromJson(text, EventOnNewMessage.class);
                        if(Objects.equals(Store.activeChat.getType(), "dou") && Objects.equals(Store.activeChat.getGroupId(), data.getGroupId())) {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("command", Command.SEND_START_CHAT_PRIVATE);
                            jsonObject.addProperty("senderId", Store.activeChat.getSender().getId());
                            jsonObject.addProperty("receiverId", Store.activeChat.getReceiver().getId());
                            WS.getInstance().getWebSocket().send(jsonObject.toString());
                        }

                        if(Objects.equals(Store.activeChat.getType(), "multi") && Objects.equals(Store.activeChat.getGroupId(), data.getGroupId())) {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("command", Command.SEND_START_CHAT_MULTI);
                            jsonObject.addProperty("userId", id);
                            jsonObject.addProperty("groupId", Store.activeChat.getGroupInfo().getId());
                            WS.getInstance().getWebSocket().send(jsonObject.toString());
                        }
                        break;
                    }
                    case Command.LISTEN_GET_CONVERSATION: {
                        EventOnGetConversations data = gson.fromJson(text, EventOnGetConversations.class);
                        List<Conversation> douConversations = data.getDouConversations();
                        List<Conversation> groupConversations = data.getGroupConversation();
                        List<Conversation> mergeConversations = new ArrayList<>();
                        mergeConversations.addAll(douConversations);
                        mergeConversations.addAll(groupConversations);
                        Store.conversations = mergeConversations;
                        Store.groupConversations = groupConversations;

                        BusConversation busConversation = new BusConversation();
                        busConversation.setConversations(mergeConversations);
                        busConversation.setGroupConversation(groupConversations);
                        EventBus.getDefault().post(busConversation);
                        break;
                    }
                    case Command.LISTEN_START_CHAT_PRIVATE: {
                        EventOnStartChatPrivate data = gson.fromJson(text, EventOnStartChatPrivate.class);
                        ActiveChat activeChat = new ActiveChat();
                        Collections.sort(data.getMessages());
                        try {
                            List<MessageGroupDay> x = MessageUlti.groupMessagesByDayAndTime(data.getMessages());
                            Collections.sort(x);
                            activeChat.setMessageGroupDay(x);
                            Log.e("TEST", gson.toJson(x));
                            activeChat.setFiles(data.getFiles());
                            activeChat.setGroupId(data.getGroupId());
                            activeChat.setReceiver(data.getReceiver());
                            activeChat.setSender(data.getSender());
                            activeChat.setType(data.getType());

                            Store.activeChat = activeChat;

                            EventBus.getDefault().post(activeChat);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case Command.LISTEN_START_CHAT_MULTI: {
                        EventOnStartChatMulti data = gson.fromJson(text, EventOnStartChatMulti.class);
                        Log.e("TEST", gson.toJson(data));
                        ActiveChat activeChat = new ActiveChat();
                        Collections.sort(data.getMessages());
                        try {
                            List<MessageGroupDay> x = MessageUlti.groupMessagesByDayAndTime(data.getMessages());
                            Collections.sort(x);
                            activeChat.setMessageGroupDay(x);
                            Log.e("TEST", gson.toJson(x));
                            activeChat.setFiles(data.getFiles());
                            activeChat.setGroupId(data.getGroupInfo().getId());
                            activeChat.setGroupInfo(data.getGroupInfo());
                            activeChat.setType(data.getType());

                            Store.activeChat = activeChat;

                            EventBus.getDefault().post(activeChat);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.i("WS", "onMessage");

            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.i("WS", "onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.i("WS", "onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                throw new RuntimeException("some", t);
//                Log.i("WS", "onFailure" + t.toString());
            }
        });
    }

    public static WS getInstance(String id) {
        if (instance == null) {
            WS.id = id;

            instance = new WS(id);
            Log.i("TEST", "TEST");
        }
        return instance;
    }

    public static WS getInstance() {
        if (instance == null) {

            instance = new WS(id);
            Log.i("TEST", "TEST");
        }
        return instance;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }
}

