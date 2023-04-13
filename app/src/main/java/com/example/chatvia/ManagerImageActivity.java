package com.example.chatvia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.chatvia.adapter.ManagerImageAdapter;
import com.example.chatvia.config.Store;
import com.example.chatvia.models.FileMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManagerImageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ManagerImageAdapter mAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_image);
        Toolbar toolbar = findViewById(R.id.managerImage_toolbar); setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_icon);
        actionBar.setTitle("Hình ảnh");

        mRecyclerView = findViewById(R.id.managerImage_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ManagerImageAdapter(this, Store.activeChat.getFiles().stream().filter(fileMessage -> Objects.equals(fileMessage.getFormat(), "image")).collect(Collectors.toList()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}