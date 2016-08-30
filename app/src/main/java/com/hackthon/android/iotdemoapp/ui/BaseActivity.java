package com.hackthon.android.iotdemoapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mayur Lathkar on 7/27/2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
    }

    protected void showProgressDialog(String message){
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(BaseActivity.this, null, message, true, false, null);
        } else {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }
}
