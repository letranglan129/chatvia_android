package com.example.chatvia.models;

import com.example.chatvia.ws.receiver.Message;

import java.util.List;

public class MessageGroupTime {
    private String time;
    private List<Message> messageList;

    public MessageGroupTime(String time, List<Message> messageList) {
        this.time = time;
        this.messageList = messageList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
