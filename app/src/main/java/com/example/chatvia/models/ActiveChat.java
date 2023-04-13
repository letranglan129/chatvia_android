package com.example.chatvia.models;

import java.util.List;

public class ActiveChat {
    private List<FileMessage> files;
    private List<MessageGroupDay> messages;

    private Group groupInfo;
    private String groupId;
    private User receiver;
    private User sender;
    private String type;

    public List<FileMessage> getFiles() {
        return files;
    }

    public void setFiles(List<FileMessage> files) {
        this.files = files;
    }

    public List<MessageGroupDay> getMessageGroupDay() {
        return messages;
    }

    public void setMessageGroupDay(List<MessageGroupDay> messages) {
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
    } public List<MessageGroupDay> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageGroupDay> messages) {
        this.messages = messages;
    }

    public Group getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(Group groupInfo) {
        this.groupInfo = groupInfo;
    }
}
