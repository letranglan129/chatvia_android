package com.example.chatvia.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatvia.R;
import com.example.chatvia.adapter.ConversationAdapter;
import com.example.chatvia.adapter.UserActiveAdapter;
import com.example.chatvia.config.Store;
import com.example.chatvia.databinding.FragmentChatTabBinding;
import com.example.chatvia.models.BusContact;
import com.example.chatvia.models.BusConversation;
import com.example.chatvia.models.Conversation;
import com.example.chatvia.models.User;
import com.example.chatvia.ws.Command;
import com.example.chatvia.ws.WS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ChatTabFragment extends Fragment {
    private FragmentChatTabBinding binding;
    private RecyclerView userActiveRecyclerView;
    private RecyclerView conversationRecyclerView;
    private SharedPreferences SP;
    private ConversationAdapter conversationAdapter;
    private UserActiveAdapter userActiveAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatTabBinding.inflate(inflater, container, false);

        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        View root = binding.getRoot();
        userActiveRecyclerView = root.findViewById(R.id.chatTab_recyclerview_useractive);
        conversationRecyclerView = root.findViewById(R.id.chatTab_recyclerview_conversation);

        userActiveAdapter = new UserActiveAdapter();
        conversationAdapter = new ConversationAdapter();

        handleRenderUserActiveRecyclerView(root);
        handleRenderConversationRecyclerView(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void handleRenderUserActiveRecyclerView(View root) {

        String userId = SP.getString("id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        userActiveRecyclerView.setLayoutManager(linearLayoutManager);
        userActiveAdapter.setContext(root.getContext(), userId);
        userActiveRecyclerView.setAdapter(userActiveAdapter);
    }

    public void handleRenderConversationRecyclerView(View root) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        String userId = SP.getString("id", "");
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationAdapter.setContext(root.getContext(), userId);
        conversationRecyclerView.setAdapter(conversationAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        JsonObject jsonGetConversation = new JsonObject();

        jsonGetConversation.addProperty("command", Command.SEND_GET_CONVERSATION);
        jsonGetConversation.addProperty("userId", SP.getString("id", ""));
        WS.getInstance().getWebSocket().send(jsonGetConversation.toString());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusConversation event) {
        conversationAdapter.setConversations(event.getConversations().stream().filter(c -> Integer.parseInt(c.countMessage) > 0).collect(Collectors.toList()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusContact event) {
        userActiveAdapter.setUsers(Store.friends.stream().filter(f -> !Objects.equals(f.getConnectid(), "-1") && f.getConnectid() != null).collect(Collectors.toList()));
    }

}
