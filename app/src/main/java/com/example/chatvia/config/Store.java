package com.example.chatvia.config;

import com.example.chatvia.models.ActiveChat;
import com.example.chatvia.models.Conversation;
import com.example.chatvia.models.User;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public static List<User> friends = new ArrayList<>();
    public static List<Conversation> conversations = new ArrayList<>();
    public static List<Conversation> groupConversations = new ArrayList<>();

    public static ActiveChat activeChat;
}
