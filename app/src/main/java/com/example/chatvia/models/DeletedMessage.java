package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class DeletedMessage {
    @SerializedName("message_id")
    private Integer messageId;
    @SerializedName("user_id")
    private Integer userId;

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
}
