package com.example.chatvia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatvia.databinding.ActivitySignInBinding;
import com.example.chatvia.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding binding;
    private SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SP = getSharedPreferences("USER", MODE_PRIVATE);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.signInTextViewSignUpNow.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        });

        binding.signInButton.setOnClickListener(v -> {
            EditText email = findViewById(R.id.signIn_editText_email);
            EditText password = findViewById(R.id.signIn_editText_password);
            try {
                signIn(email.getText().toString(), password.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signIn(String email, String password) throws JSONException {
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6) {
            Toast.makeText(this, "Mật khẩu tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue volleyQueue = Volley.newRequestQueue(this);
        String url = Config.API_URL + "/signIn";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                (Response.Listener<JSONObject>) response -> {

                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = SP.edit();
                    try {
                        editor.putString("id", response.getString("id"));
                        editor.putString("email", response.getString("email"));
                        editor.putString("fullname", response.getString("fullname"));
                        editor.putString("phone", response.getString("phone"));
                        editor.putString("connectid", response.getString("connectid"));
                        editor.putString("describe", response.getString("describe"));
                        editor.putString("avatar", response.getString("avatar"));
                        editor.apply();
                        Log.i("id", response.getString("id"));
                        startActivity(new Intent(getApplicationContext(), WrapActivity.class));
                    } catch (JSONException e) {
                        Toast.makeText(this, "Đã xảy ra lỗi!!!", Toast.LENGTH_SHORT).show();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(this, "Thông tin đăng nhập không khớp với tài khoản nào", Toast.LENGTH_SHORT).show();
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = SP.edit();
                    editor.clear();
                    editor.apply();
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }
}