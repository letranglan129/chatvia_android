package com.example.chatvia.ws.receiver;

import com.example.chatvia.models.FileMessage;
import com.example.chatvia.models.Group;
import com.example.chatvia.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventOnStartChatMulti {
    private List<FileMessage> files;
    private List<Message> messages;
    private Group groupInfo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Group getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(Group groupInfo) {
        this.groupInfo = groupInfo;
    }
}
