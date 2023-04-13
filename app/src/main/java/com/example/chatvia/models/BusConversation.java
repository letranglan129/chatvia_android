package com.example.chatvia.models;

import java.util.List;

public class BusConversation {
    public List<Conversation> conversations;
    private List<Conversation> groupConversation;

    public List<Conversation> getGroupConversation() {
        return groupConversation;
    }

    public void setGroupConversation(List<Conversation> groupConversation) {
        this.groupConversation = groupConversation;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
