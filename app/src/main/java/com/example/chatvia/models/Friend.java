package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class Friend {
    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("friend_id")
    private Integer friendId;

    @SerializedName("status")
    private String status;

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return this.friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
