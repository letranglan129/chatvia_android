package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class GroupMember {
    @SerializedName("group_id")
    private Integer groupId;
    @SerializedName("user_id")
    private Integer userId;

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
