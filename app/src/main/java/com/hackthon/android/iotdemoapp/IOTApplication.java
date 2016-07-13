package com.hackthon.android.iotdemoapp;

import android.app.Application;
import android.util.Log;

import com.hackthon.android.iotdemoapp.constants.AppSpecificConstants;
import com.hackthon.android.iotdemoapp.model.User;
import com.hackthon.android.iotdemoapp.service.MyVolley;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class IOTApplication extends Application {
    private static IOTApplication iotApplication = null;
    private User currentUser;

    public static IOTApplication getIotApplication() {
        return iotApplication;
    }

    public static void setIotApplication(IOTApplication context) {
        IOTApplication.iotApplication = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("IotApp==>","hit");
        setIotApplication(this);
        AppSpecificConstants.setConfigs(this);
        MyVolley.getInstance(this);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
