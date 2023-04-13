package com.example.chatvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chatvia.databinding.ActivityUserInfoBinding;
import com.example.chatvia.ws.WS;

public class UserInfoActivity extends BaseActivity {

    private ActivityUserInfoBinding binding;
    private SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SP = getSharedPreferences("USER", MODE_PRIVATE);

        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        loadUserInfo();
        editInfoOnClick();
    }

    private void loadUserInfo() {
        binding.userInfoEditTextName.setText(SP.getString("fullname", ""));
        binding.userInfoEditTextEmail.setText(SP.getString("email", ""));
        binding.userInfoEditTextPhone.setText(SP.getString("phone", ""));
    }

    private void editInfoOnClick() {
        binding.userInfoBtnEdit.setOnClickListener(view -> {
            binding.userInfoEditTextName.setEnabled(true);
            binding.userInfoEditTextName.setAlpha(1);
            binding.userInfoEditTextEmail.setEnabled(true);
            binding.userInfoEditTextEmail.setAlpha(1);
            binding.userInfoEditTextPhone.setEnabled(true);
            binding.userInfoEditTextPhone.setAlpha(1);

            binding.userInfoWrapConfirm.setVisibility(View.VISIBLE);
            binding.userInfoButtonChooseAvatar.setVisibility(View.VISIBLE);
            binding.userInfoBtnEdit.setVisibility(View.GONE);
        });

        binding.userInfoBtnSaveCancel.setOnClickListener(v -> {
            binding.userInfoEditTextName.setEnabled(false);
            binding.userInfoEditTextName.setAlpha((float) 0.5);
            binding.userInfoEditTextEmail.setEnabled(false);
            binding.userInfoEditTextEmail.setAlpha((float)0.5);
            binding.userInfoEditTextPhone.setEnabled(false);
            binding.userInfoEditTextPhone.setAlpha((float)0.5);

            binding.userInfoWrapConfirm.setVisibility(View.GONE);
            binding.userInfoButtonChooseAvatar.setVisibility(View.GONE);
            binding.userInfoBtnEdit.setVisibility(View.VISIBLE);
        });

        binding.userInfoBtnReturn.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}