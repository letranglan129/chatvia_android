package com.example.chatvia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatvia.databinding.ActivitySignUpBinding;
import com.example.chatvia.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.signUpTextViewSignInNow.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.signUpButton.setOnClickListener(view -> {
            EditText email = findViewById(R.id.signUp_editText_email);
            EditText fullname = findViewById(R.id.signUp_editText_fullname);
            EditText password = findViewById(R.id.signUp_editText_password);
            EditText repassword = findViewById(R.id.signUp_editText_repassword);
            try {
                signUp(email.getText().toString(), fullname.getText().toString(), password.getText().toString(), repassword.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signUp(String email, String fullname, String password, String repassword) throws JSONException {
        if (email.isEmpty() || password.isEmpty() || fullname.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(this, "Nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue volleyQueue = Volley.newRequestQueue(this);
        String url = Config.API_URL + "/signUp";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("fullname", fullname);
        jsonBody.put("password", password);
        jsonBody.put("repassword", repassword);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        String msg = response.getString("msg");
                        Boolean result = response.getBoolean("result");

                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        if (result) {
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Đã xảy ra lỗi!!!", Toast.LENGTH_SHORT).show();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(this, "Đã xảy ra lỗi!!!", Toast.LENGTH_SHORT).show();
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }
}