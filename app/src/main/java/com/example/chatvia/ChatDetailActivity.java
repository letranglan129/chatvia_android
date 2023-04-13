package com.example.chatvia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatvia.adapter.MessageListAdapter;
import com.example.chatvia.adapter.ScrollToTopDataObserver;
import com.example.chatvia.config.Store;
import com.example.chatvia.databinding.ActivityChatDetailBinding;
import com.example.chatvia.eventbus.EventActiveChat;
import com.example.chatvia.models.ActiveChat;
import com.example.chatvia.models.Messages;
import com.example.chatvia.ws.Command;
import com.example.chatvia.ws.WS;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatDetailActivity extends AppCompatActivity {
    private ActivityChatDetailBinding binding;
    private RecyclerView messagesRecyclerView;
    private NavigationView navigationView;
    private EditText editText;
    private SharedPreferences SP;
    private MessageListAdapter messageListAdapter;
    private MaterialButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SP = getSharedPreferences("USER", MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat_detail);

        messageListAdapter = new MessageListAdapter();

        Toolbar toolbar = findViewById(R.id.chatDetail_toolbar);
        messagesRecyclerView = findViewById(R.id.chatDetail_recyclerview_message);
        navigationView = findViewById(R.id.chatDetail_navigationview);
        editText = findViewById(R.id.chatDetail_message_editText);
        sendBtn = findViewById(R.id.chatDetail_sendMessage_btn);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_icon);

        handleRenderMessageRecyclerView(binding.getRoot());
        handleClickNavigationView(binding.getRoot());
        handleSendMessage(binding.getRoot());
        handleClickButtonSend();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_chatdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.chatDetail_actionBar_more:
                DrawerLayout drawerLayout = findViewById(R.id.chatDetail_drawerlayout);
                drawerLayout.open();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleLoadInfoInNavigation(ActiveChat activeChat) {
        TextView name = navigationView.findViewById(R.id.chatDetail_name_nav);
        TextView desc = navigationView.findViewById(R.id.chatDetail_desc_nav);
        RoundedImageView avatar = navigationView.findViewById(R.id.chatDetail_avatar_nav);

        if(Objects.equals(activeChat.getType(), "multi")) {
            name.setText(activeChat.getGroupInfo().getName());
            desc.setText(activeChat.getGroupInfo().getDesc());

            if(activeChat.getGroupInfo().getAvatar() == null || activeChat.getGroupInfo().getAvatar().isEmpty()) {
                Glide.with(this).load(R.drawable.no_avatar).centerCrop().into(avatar);
            } else {
                Glide.with(this).load(activeChat.getGroupInfo().getAvatar()).error(R.drawable.no_avatar).centerCrop().into(avatar);
            }

        } else {
            name.setText(activeChat.getReceiver().getFullname());
            desc.setText(activeChat.getReceiver().getDescribe());

            if(activeChat.getReceiver().getAvatar() == null || activeChat.getReceiver().getAvatar().isEmpty()) {
                Glide.with(this).load(R.drawable.no_avatar).centerCrop().into(avatar);
            } else {
                Glide.with(this).load(activeChat.getReceiver().getAvatar()).error(R.drawable.no_avatar).centerCrop().into(avatar);
            }
        }

    }

    public void handleRenderMessageRecyclerView(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(false);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messageListAdapter.setContext(root.getContext(), SP.getString("id", ""));
        messagesRecyclerView.setAdapter(messageListAdapter);
    }

    public void handleClickNavigationView(View root) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.chatDetail_item_image:
                        Intent intent = new Intent(root.getContext(), ManagerImageActivity.class);
                        root.getContext().startActivity(intent);
                        break;
                    case R.id.chatDetail_item_file:
                        Toast.makeText(root.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chatDetail_item_member:
                        Toast.makeText(root.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chatDetail_item_delete:
                        Toast.makeText(root.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chatDetail_item_out:
                        Toast.makeText(root.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void handleSendMessage(View root) {
        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String text = editText.getText().toString();
                if(!text.trim().isEmpty())
                    actionSendMessage(text);
                return true;
            }
            return false;

        });
    }

    public void handleClickButtonSend() {
        sendBtn.setOnClickListener(view -> {
            String text = editText.getText().toString();
            if(!text.trim().isEmpty())
                actionSendMessage(text);
        });
    }

    public void actionSendMessage(String text) {
        String userId = SP.getString("id", "");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("senderId", userId);;
        jsonObject.addProperty("groupId", Store.activeChat.getGroupId());;
        jsonObject.addProperty("msg",  text);
        jsonObject.addProperty("command", Command.SEND_NEW_MESSAGE);
        WS.getInstance().getWebSocket().send(jsonObject.toString());

        editText.setText("");
        editText.requestFocus();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("jsonGetChatDetail") != null)
            WS.getInstance().getWebSocket().send(intent.getStringExtra("jsonGetChatDetail"));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ActiveChat event) {
        Gson gson = new Gson();
        messageListAdapter.setMessages(event.getMessageGroupDay());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        if(Objects.equals(event.getType(), "multi"))
            actionBar.setTitle(event.getGroupInfo().getName());
        else
            actionBar.setTitle(event.getReceiver().getFullname());
        handleLoadInfoInNavigation(event);
    }
}