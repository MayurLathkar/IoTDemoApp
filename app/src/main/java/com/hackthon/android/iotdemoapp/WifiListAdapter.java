package com.hackthon.android.iotdemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Mayur Lathkar on 7/5/2016.
 */
public class WifiListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public WifiListAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_wifi_device, parent, false);
        ((TextView)itemView.findViewById(R.id.tv_name)).setText(String.valueOf(getItem(position)));
        return itemView;
    }
}
