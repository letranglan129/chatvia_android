package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class Notifications {
    @SerializedName("id")
    private Integer id;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("type")
    private String type;

    @SerializedName("payload")
    private String payload;

    @SerializedName("created_at")
    private java.sql.Timestamp createdAt;

    @SerializedName("seen_at")
    private java.sql.Timestamp seenAt;

    @SerializedName("from_id")
    private Integer fromId;

    @SerializedName("is_readed")
    private Boolean isReaded;

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public java.sql.Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public java.sql.Timestamp getSeenAt() {
        return this.seenAt;
    }

    public void setSeenAt(java.sql.Timestamp seenAt) {
        this.seenAt = seenAt;
    }

    public Integer getFromId() {
        return this.fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Boolean getIsReaded() {
        return this.isReaded;
    }

    public void setIsReaded(Boolean isReaded) {
        this.isReaded = isReaded;
    }
}
