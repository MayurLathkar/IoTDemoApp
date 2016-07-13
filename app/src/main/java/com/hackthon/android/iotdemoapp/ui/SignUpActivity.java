package com.hackthon.android.iotdemoapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hackthon.android.iotdemoapp.R;
import com.hackthon.android.iotdemoapp.model.User;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignUp;
    private View tvSignUp;
    private EditText userName, password, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        intializeAll();
    }

    private void intializeAll() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvSignUp =  findViewById(R.id.signup);
        userName = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.password);
        mobile = (EditText) findViewById(R.id.mobile);
        btnSignUp.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp :
                performSignUp(userName.getEditableText().toString(), password.getEditableText().toString(), mobile.getEditableText().toString());
                break;

            case R.id.signup :
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void performSignUp(String userName, String password, String mobile) {
        HashMap<String, String> body = new HashMap<>();
        body.put("name", userName);
        body.put("password", password);
        body.put("phone", mobile);

        com.android.volley.Response.Listener<User> listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                Toast.makeText(SignUpActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, IOTAppIntroActivity.class);
                startActivity(intent);
                finish();
            }
        };

        com.android.volley.Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        BaseRequestClass.userSignUpRequest(SignUpActivity.this, body, listener, onError);
    }
}
