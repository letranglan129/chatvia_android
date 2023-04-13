package com.example.chatvia.config;

public class Config {
    public static final String IP = "192.168.0.107";
    public static final String API_PORT = "8080";
    public static final String WS_PORT = "9001";

    public static final String API_URL = "http://" + Config.IP + ":" + API_PORT;
    public static final String WS_URL = "ws://" + Config.IP + ":" + WS_PORT;

}
