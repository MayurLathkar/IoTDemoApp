package com.hackthon.android.iotdemoapp.constants;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.hackthon.android.iotdemoapp.util.Util;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class AppSpecificConstants {

    public static String BASE_URL = "http://52.38.208.136:1337/";
    public static String SERVER_CACHE_DIRECTORY = "";
    public static String APP_VERSION_CODE = "";

    public static String signInUser = "user/signin";
    public static String signUpUser = "user/signup";

    public static void setConfigs(Context context) {
        Resources resources = context.getResources();
        //PUSH_SENDER_ID = resources.getString(R.string.push_sender_id);

        SERVER_CACHE_DIRECTORY = new Util().getStorageLocation(context);
        try {
            APP_VERSION_CODE = "" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseApi() {
        return BASE_URL;
    }

    public static String getResourcePrefix() {
        return getBaseApi();
    }
}
