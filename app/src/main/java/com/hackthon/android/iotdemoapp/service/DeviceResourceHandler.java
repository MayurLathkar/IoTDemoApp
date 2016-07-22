package com.hackthon.android.iotdemoapp.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mayur Lathkar on 7/22/2016.
 */
public class DeviceResourceHandler {
    private Context mContext = null;
    private SharedPreferences mPref = null;
    private SharedPreferences.Editor mEditor = null;

    public DeviceResourceHandler(Context context) {
        mContext = context;
    }

    public void addToSharedPref(String prefName, String data) {
        mPref = mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        mEditor.putString(prefName, data);
        mEditor.commit();
    }

    public String getDataFromSharedPref(String prefName) {
        mPref = mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return mPref.getString(prefName, null);
    }

    public void clearSharedPref(String prefName) {
        mPref = mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        mEditor.remove(prefName);
        mEditor.commit();
    }
}
