package com.example.chatvia.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("phone")
    private String phone;

    @SerializedName("connectid")
    private String connectid;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("describe")
    private String describe;

    public User(String id, String email, String password, String fullname, String phone, String connectid, String avatar, String describe) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.connectid = connectid;
        this.avatar = avatar;
        this.describe = describe;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConnectid() {
        return this.connectid;
    }

    public void setConnectid(String connectid) {
        this.connectid = connectid;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
