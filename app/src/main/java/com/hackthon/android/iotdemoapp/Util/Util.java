package com.hackthon.android.iotdemoapp.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Mayur Lathkar on 7/13/2016.
 */
public class Util {
    public String getStorageLocation(Context context) {
        String dir = null;
        try {
            dir = context.getFilesDir().getAbsolutePath();
            boolean mExternalStorageAvailable = false;
            boolean mExternalStorageWriteable = false;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                mExternalStorageAvailable = mExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states,
                // but all we need
                // to know is we can neither read nor write
                mExternalStorageAvailable = mExternalStorageWriteable = false;
            }

            if (mExternalStorageWriteable) {
                if (checkOsVersionAbove_7()) {
                    dir = context.getExternalFilesDir(null).getAbsolutePath();
                } else {
                    dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
                            + context.getPackageName();
                }

            } else if (dir == null || dir.contains("null")) {
                dir = "/data/data/" + context.getPackageName();
            }

        } catch (Exception e) {
            if (dir == null || dir.contains("null")) {
                dir = "/data/data/" + context.getPackageName();
                File file = new File(dir);
                file.mkdirs();

            } else {
                dir = context.getCacheDir().getAbsolutePath();

            }
            e.printStackTrace();
        }

        return dir;
    }

    public boolean checkOsVersionAbove_7() {
        if (android.os.Build.VERSION.SDK_INT > 7)
            return true;
        else
            return false;

    }
}
