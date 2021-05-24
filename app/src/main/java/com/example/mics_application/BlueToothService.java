package com.example.mics_application;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

public class BlueToothService extends Service {
    BroadcastReceiver b_receiver_b;
    public BlueToothService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);       // Make our phone discoverable in order for other people to find us
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        b_receiver_b = new BroadcastReceiver() {                                                    // Create a new BroadcastReceiver waiting to receive a given action from the bluetooth research
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    System.out.println("FOUND DEVICE");
                    Intent in = new Intent("service_users_activity");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceMAC = device.getAddress();
                    in.putExtra("NAME",deviceName);
                    in.putExtra("MAC",deviceName);
                    sendBroadcast(in);


                }
            }


        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(b_receiver_b, filter);                                                     // Register to produce a broadcast from bluetooth research

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();                   // Start the bluetooth devices search
        bluetoothAdapter.startDiscovery();
        Toast.makeText(this, R.string.service_look, Toast.LENGTH_LONG).show();
        System.out.println("IN THE SERVICEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        System.out.println("OOOOOOOOOOUUUUUUUUUUUTTTTTTTTTTTTTT");
        unregisterReceiver(b_receiver_b);
        super.onDestroy();

    }

}