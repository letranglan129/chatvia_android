package com.example.chatvia.models;

import java.util.List;

public class Messages {
    private String name;
    private int avatar;
    private List<String> messages;
    private boolean isSeen;
    private boolean isSelf;

    public Messages(String name, int avatar, List<String> messages, boolean isSeen, boolean isSelf) {
        this.name = name;
        this.avatar = avatar;
        this.messages = messages;
        this.isSeen = isSeen;
        this.isSelf = isSelf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }
}
