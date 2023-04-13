package com.example.chatvia.ws;

public class Command {
    public static final String LISTEN_START_CHAT_MULTI = "onStartChatMulti";
    public static final String LISTEN_START_CHAT_PRIVATE = "onStartChatPrivate";
    public static final String LISTEN_GET_CONVERSATION = "onGetConversation";
    public static final String LISTEN_NEW_MESSAGE = "onNewMessage";
    public static final String LISTEN_GET_ONLINE = "onGetOnline";
    public static final String LISTEN_GET_FRIENDS = "onGetFriends";

    public static final String SEND_GET_ONLINE = "getOnline";
    public static final String SEND_NEW_MESSAGE = "sendMessage";
    public static final String SEND_START_CHAT_PRIVATE = "startChatPrivate";
    public static final String SEND_START_CHAT_MULTI = "startChatMulti";
    public static final String SEND_GET_CONVERSATION = "getConversation";
}
