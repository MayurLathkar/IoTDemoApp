package com.hackthon.android.iotdemoapp.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.hackthon.android.iotdemoapp.constants.AppSpecificConstants;
import com.hackthon.android.iotdemoapp.model.User;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class SignUpRequest<T> extends Request<T> {

    private Context context;
    private HashMap<String, String> params;
    private Type type;
    private Response.Listener<T> listener;
    private Gson mGson;
    private Priority priority;

    public SignUpRequest(Context context, HashMap<String, String> params, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, AppSpecificConstants.getBaseApi() + AppSpecificConstants.signUpUser, errorListener);
        this.context = context;
        this.type = type;
        this.params = params;
        this.listener = listener;
        this.mGson = new Gson();
    }

    public byte[] getBody() throws AuthFailureError {
        if (params != null && params.size() > 0) {
            JSONObject obj = new JSONObject(params);
            return obj.toString().getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.v("TAG", json);
            return (Response<T>) Response.success(mGson.fromJson(json, type),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("TAG", e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        User result = (User) response;
        if (listener != null)
            listener.onResponse((T) result);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

}
