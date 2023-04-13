package com.example.chatvia.ws.receiver;

public class WSEventReceiver {

    private String event;

    public WSEventReceiver(String event) {
        this.event = event;
    }

    public WSEventReceiver() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
