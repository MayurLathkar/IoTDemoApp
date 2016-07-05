package com.hackthon.android.iotdemoapp;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Device list.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 */
public class DeviceListActivity extends Activity {
    private ListView mListView;
    private DeviceListAdapter mAdapter;
    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    showToast("Paired");
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showToast("Unpaired");
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };
    private UUID myUUID;
    private ArrayList<BluetoothDevice> mDeviceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_list);

        mDeviceList = getIntent().getExtras().getParcelableArrayList("device.list");

        mListView = (ListView) findViewById(R.id.lv_paired);

        mAdapter = new DeviceListAdapter(this);

        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);

                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    unpairDevice(device);
                } else {
                    showToast("Pairing...");

                    pairDevice(device);
                }
            }
        });

        mListView.setAdapter(mAdapter);

        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mPairReceiver);

        super.onDestroy();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void pairDevice(BluetoothDevice device) {
        Log.d("Device Paired ==>", device.getName() + " -- " + device.toString());
        try {

//            int bt_port_to_connect = 1; // just an example, could be any port number you wish
//            BluetoothDevice bDevice = device; // get the bluetooth device (e.g., using bt discovery)
//            BluetoothSocket deviceSocket = null;
//            Method m1 = device.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
//            m1.invoke(device, 1);
//            deviceSocket = (BluetoothSocket) m1.invoke(device, bt_port_to_connect);
//            deviceSocket.connect();

            Log.d("Not thread ==>", "starting thread");

            ThreadConnectBTdevice threadConnectBTdevice = new ThreadConnectBTdevice(device);
            threadConnectBTdevice.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ThreadConnectBTdevice extends Thread {
        private final BluetoothDevice bluetoothDevice;
        private BluetoothSocket bluetoothSocket = null;


        public ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;
            Log.d("Thread ==>", "Connected");
        }

        @Override
        public void run() {
            boolean success = false;
            try {

                try {

                    int bt_port_to_connect = 1; // just an example, could be any port number you wish
                    BluetoothDevice bDevice = bluetoothDevice; // get the bluetooth device (e.g., using bt discovery)
                    BluetoothSocket deviceSocket = null;
                    Method m1 = null;
                    m1 = bDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
                    m1.invoke(bDevice, 1);
                    bluetoothSocket = (BluetoothSocket) m1.invoke(bDevice, bt_port_to_connect);
                    bluetoothSocket.connect();
                    Log.d("Thread ==>", "connected in run");
//                    byte[] byteString = ("ping").getBytes();
//                    bluetoothSocket.getOutputStream().write(byteString);


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    Log.d("Exception1 ==> ", e.toString() + " ...");
                } catch (InvocationTargetException e) {
                    Log.d("Exception2 ==> ", e.toString() + " ...");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    Log.d("Exception3 ==> ", e.toString() + " ...");
                    e.printStackTrace();
                }


                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                Log.d("Thread ==> ", "emsg" + eMessage);
                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (success) {
                System.out.println("Success ==>");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            OutputStream outStream = bluetoothSocket.getOutputStream();
                            InputStream inputStream = bluetoothSocket.getInputStream();
                            byte[] byteString = ("ping").getBytes();


                            outStream.write(byteString);
                            Log.d("BLUETOOTH_COMMS ==>", "pinging");

                            Log.d("Input Stream ==>", inputStream.toString() + " input stream");
                            System.out.println("Input Stream ==>" + inputStream.toString() + " input stream");


                            Log.d("Converting ==> ", "Starting input stream");
                            byte[] buffer = new byte[1024];
                            DataInputStream mmInStream = new DataInputStream(inputStream);
                            int bytes = mmInStream.read(buffer);
                            String readMessage = new String(buffer, 0, bytes);
                            Log.d("Msg received ==>", readMessage + " ---");
                            Log.d("Converting ==> ", "Ending input stream");
                            if (!readMessage.isEmpty()) {
                                byte[] wifiList = ("wifi-list").getBytes();
                                outStream.write(wifiList);
                                mmInStream = new DataInputStream(inputStream);
                                bytes = mmInStream.read(buffer);
                                final String listOfWifi = new String(buffer, 0, bytes);
                                Log.d("Wifi List==>", listOfWifi);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(DeviceListActivity.this, WifiActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("WifiList", listOfWifi);
                                        i.putExtras(bundle);
                                        startActivity(i);
                                    }
                                });
                            }


                        } catch (IOException e) {
                            Log.d("BLUETOOTH_COMMS ==>", e.getMessage());
                        }
                        Log.d("BLUETOOTH_COMMS ==>", "connected successfully");


                    }
                });
            } else {
            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}