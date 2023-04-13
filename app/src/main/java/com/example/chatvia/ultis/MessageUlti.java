package com.example.chatvia.ultis;

import android.content.Intent;
import android.util.Log;

import com.example.chatvia.models.MessageGroupDay;
import com.example.chatvia.ws.receiver.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageUlti {
    public static List<MessageGroupDay> groupMessagesByDayAndTime(List<Message> messages) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MessageGroupDay> result = new ArrayList<>();
        String lastDate = null;
        MessageGroupDay lastArr = null;

        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            String date = message.getSentAt().substring(0, 10);
            if (!date.equals(lastDate)) {
                lastDate = date;
            } else  {
                lastArr = getMessageGroupDay(result, date);
            }

            Message lastMessage = lastArr == null || lastArr.getMessageList().isEmpty() ? null : lastArr.getMessageList().get(lastArr.getMessageList().size() - 1).get(lastArr.getMessageList().get(lastArr.getMessageList().size() - 1).size() -1);

            if (lastMessage != null) {
                long x = dateFormat.parse(message.getSentAt()).getTime() / 1000;
                long y = dateFormat.parse(lastMessage.getSentAt()).getTime() / 1000;
                if (x - y <= 600 && Objects.equals(message.getSenderId(), lastMessage.getSenderId())) {
                    lastArr.getMessageList().get(lastArr.getMessageList().size() - 1).add(message);
                } else  {
                    List<Message> newArr = new ArrayList<>();
                    newArr.add(message);
                    lastArr.getMessageList().add(newArr);
                }
            } else {
                lastArr = new MessageGroupDay(date, new ArrayList<>());
                List<Message> newArr = new ArrayList<>();
                newArr.add(message);
                lastArr.getMessageList().add(newArr);
                result.add(lastArr);
                lastArr = null;
            }
        }
        return result;
    }

    private static MessageGroupDay getMessageGroupDay(List<MessageGroupDay> list, String day) {
        for (MessageGroupDay messageGroup : list) {
            if (messageGroup.getDay().equals(day)) {
                return messageGroup;
            }
        }
        return null;
    }
}
