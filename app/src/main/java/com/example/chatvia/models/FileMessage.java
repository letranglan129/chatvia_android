package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class FileMessage {
    @SerializedName("id")
    private String id;

    @SerializedName("message_id")
    private String messageId;

    @SerializedName("href")
    private String href;

    @SerializedName("name")
    private String name;

    @SerializedName("size")
    private String size;

    @SerializedName("format")
    private String format;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
