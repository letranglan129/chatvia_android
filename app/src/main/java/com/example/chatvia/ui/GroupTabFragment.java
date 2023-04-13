package com.example.chatvia.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatvia.R;
import com.example.chatvia.adapter.ContactAdapter;
import com.example.chatvia.adapter.ConversationAdapter;
import com.example.chatvia.config.Store;
import com.example.chatvia.databinding.FragmentContactTabBinding;
import com.example.chatvia.databinding.FragmentGroupTabBinding;
import com.example.chatvia.models.BusConversation;
import com.example.chatvia.models.Conversation;
import com.example.chatvia.models.User;
import com.example.chatvia.ws.Command;
import com.example.chatvia.ws.WS;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class GroupTabFragment extends Fragment {
    private FragmentGroupTabBinding binding;
    private RecyclerView groupRecyclerView;
    private SharedPreferences SP;
    private  ConversationAdapter conversationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentGroupTabBinding.inflate(inflater, container, false);
         View root = binding.getRoot();
        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        groupRecyclerView = root.findViewById(R.id.groupTab_recyclerview_groupitem);

        conversationAdapter = new ConversationAdapter();

        handleRenderContacts(root);
        return root;
    }

    public void handleRenderContacts(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        groupRecyclerView.setLayoutManager(linearLayoutManager);
        String userId = SP.getString("id", "");
        conversationAdapter.setContext(root.getContext(),  userId);
        groupRecyclerView.setAdapter(conversationAdapter);
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
        conversationAdapter.setConversations(event.getGroupConversation());
    }

}