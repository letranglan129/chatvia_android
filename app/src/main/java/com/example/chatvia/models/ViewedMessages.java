package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class ViewedMessages {
    @SerializedName("message_id")
    private Integer messageId;
    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("viewed_at")
    private java.sql.Timestamp viewedAt;

    public Integer getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public java.sql.Timestamp getViewedAt() {
        return this.viewedAt;
    }

    public void setViewedAt(java.sql.Timestamp viewedAt) {
        this.viewedAt = viewedAt;
    }
}
