package com.hackthon.android.iotdemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class WifiActivity extends AppCompatActivity {

    String[] list ;
    ArrayList<String> wifiList = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        String listOfWifi = getIntent().getExtras().getString("WifiList");
        list = listOfWifi.split(",");
        for (String listItem : list){
            wifiList.add(listItem);
        }
        listView = (ListView) findViewById(R.id.listView);
        WifiListAdapter adapter = new WifiListAdapter(WifiActivity.this, wifiList);
        listView.setAdapter(adapter);
    }
}
