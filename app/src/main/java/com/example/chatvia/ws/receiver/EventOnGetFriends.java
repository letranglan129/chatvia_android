package com.example.chatvia.ws.receiver;

import com.example.chatvia.models.Friend;
import com.example.chatvia.models.User;

import java.util.List;

public class EventOnGetFriends {

    private String event;
    private List<User> friends;
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
