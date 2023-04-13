package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class BlockedUser {
    @SerializedName("id")
    private Integer id;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("blocked_user_id")
    private Integer blockedUserId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlockedUserId() {
        return this.blockedUserId;
    }

    public void setBlockedUserId(Integer blockedUserId) {
        this.blockedUserId = blockedUserId;
    }
}
