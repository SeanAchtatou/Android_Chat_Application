package com.example.mics_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BlueToothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("service_users_activity")){
            String text = "WE ARE IN THE RECEIVER!";
            Toast.makeText(context,text,Toast.LENGTH_LONG).show();
            
        }


    }
}