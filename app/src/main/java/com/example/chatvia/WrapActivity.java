package com.example.chatvia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import com.example.chatvia.adapter.ContactAdapter;
import com.example.chatvia.adapter.NavigationViewPagerAdapter;
import com.example.chatvia.config.Store;
import com.example.chatvia.databinding.*;
import com.example.chatvia.models.ActiveChat;
import com.example.chatvia.ws.Command;
import com.example.chatvia.ws.WS;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class WrapActivity extends BaseActivity {

    private ActivityWrapBinding binding;
    private ViewPager2 viewPager2;
    private BottomNavigationView navView;
    private Fragment currentFragment;
    private SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SP = getSharedPreferences("USER", MODE_PRIVATE);
        String userId = SP.getString("id","");
        WS ws = WS.getInstance(userId);

        if(userId.equals("")) {
            this.finish();
            Intent intent = new Intent(this, SignInActivity.class);
            this.startActivity(intent);
        }

        super.onCreate(savedInstanceState);

        binding = ActivityWrapBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        viewPager2 = findViewById(R.id.wrap_viewpager2);
        navView = findViewById(R.id.bottom_navigation_view);

        setViewPager2PageChangeListener();
        setBottomNavigationItemSelectedListener();
        handleGetOnline();
    }

    private void setBottomNavigationItemSelectedListener() {
        navView.setItemIconTintList(null);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navView.setItemIconTintList(null);
                switch (item.getItemId()) {
                    case R.id.chatTab:
                        viewPager2.setCurrentItem(0);
                        item.setIcon(R.drawable.message_primary);
                        break;
                    case R.id.contactTab:
                        viewPager2.setCurrentItem(1);
                        item.setIcon(R.drawable.contact_primary);
                        break;
                    case R.id.groupTab:
                        viewPager2.setCurrentItem(2);
                        item.setIcon(R.drawable.group_primary);
                        break;
                    case R.id.settingTab:
                        viewPager2.setCurrentItem(3);
                        item.setIcon(R.drawable.setting_primary);
                        break;
                }
                return true;
            }
        });
    }

    private void setViewPager2PageChangeListener() {
        NavigationViewPagerAdapter navigationViewPager = new NavigationViewPagerAdapter(this);
        viewPager2.setAdapter(navigationViewPager);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navView.setItemIconTintList(null);
                switch (position) {
                    case 1:
                        navView.getMenu().getItem(position).setIcon(R.drawable.contact_primary);
                        navView.getMenu().findItem(R.id.contactTab).setChecked(true);
                        break;
                    case 2:
                        navView.getMenu().getItem(position).setIcon(R.drawable.group_primary);
                        navView.getMenu().findItem(R.id.settingTab).setChecked(true);
                        break;
                    case 3:
                        navView.getMenu().getItem(position).setIcon(R.drawable.setting_primary);
                        navView.getMenu().findItem(R.id.settingTab).setChecked(true);
                        break;
                    default:
                        navView.getMenu().getItem(position).setIcon(R.drawable.message_primary);
                        navView.getMenu().findItem(R.id.chatTab).setChecked(true);
                        break;
                }
            }
        });
    }

    private void handleGetOnline() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String userId = SP.getString("id", "");
                        if (!userId.equals("")) {
                            JsonObject jsonGetOnline = new JsonObject();
                            jsonGetOnline.addProperty("command", Command.SEND_GET_ONLINE);
                            jsonGetOnline.addProperty("id", userId);
                            WS.getInstance().getWebSocket().send(jsonGetOnline.toString());

//                            userActiveAdapter.setUsers(Store.friends.stream().filter(f -> !Objects.equals(f.getConnectid(), "-1") && f.getConnectid() != null).collect(Collectors.toList()));
                        }
                    }
                });
            }
        }, 5000, 5000);
    }
}