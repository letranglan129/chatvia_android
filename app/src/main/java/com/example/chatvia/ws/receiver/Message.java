package com.example.chatvia.ws.receiver;

import com.example.chatvia.models.File;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Message implements Comparable<Message> {
    @SerializedName("id")
    private String id;

    @SerializedName("sender_id")
    private String senderId;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("message")
    private String message;

    @SerializedName("sent_at")
    private String sentAt;

    @SerializedName("viewed_at")
    private String viewedAt;

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("format")
    private String format;

    @SerializedName("images")
    private List<File> files;

    @SerializedName("file_id")
    private String fileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public int compareTo(Message message) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(this.sentAt);
            Date otherDate = format.parse(message.getSentAt());
            return date.compareTo(otherDate);
        } catch (ParseException e) {
            return Integer.compare(Integer.parseInt(message.getId()),Integer.parseInt(this.id));
        }
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
