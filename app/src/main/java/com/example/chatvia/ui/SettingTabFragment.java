package com.example.chatvia.ui;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatvia.R;
import com.example.chatvia.SignInActivity;
import com.example.chatvia.SignUpActivity;
import com.example.chatvia.UserInfoActivity;
import com.example.chatvia.adapter.SettingListViewAdapter;
import com.example.chatvia.databinding.FragmentChatTabBinding;
import com.example.chatvia.databinding.FragmentSettingTabBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SettingTabFragment extends Fragment {
    private FragmentSettingTabBinding binding;

    public String[] data = {"Thông tin", "Hình ảnh đã gửi", "File đã gửi", "Quyền riêng tư", "Bảo mật", "Trợ giúp", "Đăng xuất"};
    private SharedPreferences SP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingTabBinding.inflate(inflater, container, false);
        SP = requireActivity().getSharedPreferences("USER", MODE_PRIVATE);
        View root = binding.getRoot();

        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(binding.getRoot().getContext(), data);

        ListView listView = root.findViewById(R.id.listView);
        TextView fullname = root.findViewById(R.id.settingTab_fullname_textview);

        listView.setAdapter(settingListViewAdapter);
        fullname.setText(SP.getString("fullname",""));

        renderItemListView(listView);

        return root;
    }

    private void renderItemListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        startActivity(new Intent(getActivity(), UserInfoActivity.class));
                        break;
                    }
                    case 6: {
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = SP.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(getActivity(), SignInActivity.class));
                    }
                }
            }
        });
    }
}