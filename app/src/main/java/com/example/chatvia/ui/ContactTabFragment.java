package com.example.chatvia.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatvia.R;
import com.example.chatvia.adapter.ContactAdapter;
import com.example.chatvia.config.Store;
import com.example.chatvia.databinding.FragmentContactTabBinding;
import com.example.chatvia.models.ActiveChat;
import com.example.chatvia.models.BusContact;
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

public class ContactTabFragment extends Fragment {
    private FragmentContactTabBinding binding;
    private RecyclerView contactRecyclerView;
    private SharedPreferences SP;
    private ContactAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        contactRecyclerView = root.findViewById(R.id.contactTab_recyclerview_contactitem);

        contactAdapter = new ContactAdapter();

        handleRenderContacts(root);
        return root;
    }

    public void handleRenderContacts(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        contactRecyclerView.setLayoutManager(linearLayoutManager);
        contactAdapter.setContext(root.getContext(), SP.getString("id", ""));
        contactRecyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        JsonObject jsonGetOnline = new JsonObject();
        jsonGetOnline.addProperty("command", Command.SEND_GET_ONLINE);
        jsonGetOnline.addProperty("id", SP.getString("id", ""));
        WS.getInstance().getWebSocket().send(jsonGetOnline.toString());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusContact event) {
        contactAdapter.setUsers(event.getUsers());
    }


}