package com.hackthon.android.iotdemoapp.ui;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.hackthon.android.iotdemoapp.Request.LoginRequest;
import com.hackthon.android.iotdemoapp.Request.SignUpRequest;
import com.hackthon.android.iotdemoapp.model.User;
import com.hackthon.android.iotdemoapp.service.MyVolley;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class BaseRequestClass {

    public static LoginRequest<User> userLoginRequest(Context context, HashMap<String, String> params, Response.Listener<User> listener, Response.ErrorListener errorListener){
        RequestQueue queue = MyVolley.getInstance().getRequestQueue();
        Type type = new TypeToken<User>() {
        }.getType();
        final LoginRequest<User> loginRequest = new LoginRequest<>(context, params, type, listener, errorListener);
        queue.add(loginRequest);
        return loginRequest;
    }

    public static SignUpRequest<User> userSignUpRequest(Context context, HashMap<String, String> params, Response.Listener<User> listener, Response.ErrorListener errorListener){
        RequestQueue queue = MyVolley.getInstance().getRequestQueue();
        Type type = new TypeToken<User>() {
        }.getType();
        final SignUpRequest<User> signUpRequest = new SignUpRequest<>(context, params, type, listener, errorListener);
        queue.add(signUpRequest);
        return signUpRequest;
    }
}
