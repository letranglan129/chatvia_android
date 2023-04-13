package com.example.chatvia.ws.receiver;

import com.example.chatvia.models.Conversation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventOnGetConversations {
    @SerializedName("conversation")
    private List<Conversation> douConversations;

    @SerializedName("groupConversation")
    private List<Conversation> groupConversation;

    public List<Conversation> getDouConversations() {
        return douConversations;
    }

    public void setDouConversations(List<Conversation> douConversations) {
        this.douConversations = douConversations;
    }

    public List<Conversation> getGroupConversation() {
        return groupConversation;
    }

    public void setGroupConversation(List<Conversation> groupConversation) {
        this.groupConversation = groupConversation;
    }
}
