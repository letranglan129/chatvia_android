package com.example.chatvia.ws.receiver;

import com.example.chatvia.models.FileMessage;
import com.example.chatvia.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventOnStartChatPrivate {
    private List<FileMessage> files;
    private List<Message> messages;
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("receiver")
    private User receiver;
    @SerializedName("sender")
    private User sender;
    @SerializedName("type")
    private String type;

    public List<FileMessage> getFiles() {
        return files;
    }

    public void setFiles(List<FileMessage> files) {
        this.files = files;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
