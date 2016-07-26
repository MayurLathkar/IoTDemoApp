package com.hackthon.android.iotdemoapp;

import android.app.Application;
import android.util.Log;

import com.hackthon.android.iotdemoapp.constants.AppSpecificConstants;
import com.hackthon.android.iotdemoapp.model.User;
import com.hackthon.android.iotdemoapp.service.DeviceResourceHandler;
import com.hackthon.android.iotdemoapp.service.MyVolley;
import com.hackthon.android.iotdemoapp.service.Serializer;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class IOTApplication extends Application {
    private static IOTApplication iotApplication = null;
    private User currentUser;
    private DeviceResourceHandler deviceResourceHandler = null;

    public static IOTApplication getIotApplication() {
        return iotApplication;
    }

    public static void setIotApplication(IOTApplication context) {
        IOTApplication.iotApplication = context;
    }

    public void initializeUser(User currentUser){
        this.initializeUser(currentUser, true);
    }

    public void initializeUser(User currentUser, boolean forceWrite) {
        if (this.getDeviceResourceHandler().getDataFromSharedPref("current_user") == null || forceWrite) {
            this.getDeviceResourceHandler().clearSharedPref("current_user");
            this.getDeviceResourceHandler().addToSharedPref("current_user", Serializer.serialize(currentUser));
        }

        this.currentUser = currentUser;
    }

    public DeviceResourceHandler getDeviceResourceHandler() {
        if (deviceResourceHandler == null)
            deviceResourceHandler = new DeviceResourceHandler(this);
        return deviceResourceHandler;
    }

    public User getCurrentUser(){
        try {
            String e = null;
            if (this.currentUser == null && (e = this.getDeviceResourceHandler().getDataFromSharedPref("current_user")) != null) {
                this.currentUser = (User) Serializer.deserialize(e);
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.currentUser;
    }

    public void logout(){
        IOTApplication.getIotApplication().initializeUser(null, true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("IotApp==>","hit");
        setIotApplication(this);
        AppSpecificConstants.setConfigs(this);
        MyVolley.getInstance(this);
    }

}
