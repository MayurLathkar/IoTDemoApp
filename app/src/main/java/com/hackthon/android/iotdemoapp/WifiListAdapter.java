package com.hackthon.android.iotdemoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Mayur Lathkar on 7/5/2016.
 */
public class WifiListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<>();
    private OutputStream outputStream;

    public WifiListAdapter(Context context, ArrayList<String> list, OutputStream outputStream){
        this.context = context;
        this.list = list;
        this.outputStream = outputStream;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_wifi_device, parent, false);
        ((TextView)itemView.findViewById(R.id.tv_name)).setText(String.valueOf(getItem(position)));
        itemView.findViewById(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View infoDialogView = inflater.inflate(R.layout.dialog_single_button_with_edittext, null);
                final android.support.v7.app.AlertDialog infoDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
                infoDialog.setView(infoDialogView);
                ((TextView) infoDialogView.findViewById(R.id.tvName)).setText(list.get(position));
                final String password = ((TextView) infoDialogView.findViewById(R.id.etInputText)).getEditableText().toString();
                infoDialogView.findViewById(R.id.btnSingle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byte[] wifiData = (list.get(position) + " , " + password).getBytes();
                        try {
                            outputStream.write(wifiData);
                        } catch (IOException e) {
                            Log.d("Exception found==>", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return itemView;
    }
}
