package com.example.chatvia.models;

import com.example.chatvia.ws.receiver.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MessageGroupDay implements Comparable<MessageGroupDay> {
    private String day;
    private List<List<Message>> messageList;

    public MessageGroupDay(String day, List<List<Message>> messageList) {
        this.day = day;
        this.messageList = messageList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<List<Message>> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<List<Message>> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int compareTo(MessageGroupDay messageGroupDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(this.getDay());
            Date otherDate = format.parse(messageGroupDay.getDay());
            assert date != null;
            assert otherDate != null;
            return otherDate.compareTo(date);
        } catch (ParseException e) {
            return 0;
        }
    }
}
