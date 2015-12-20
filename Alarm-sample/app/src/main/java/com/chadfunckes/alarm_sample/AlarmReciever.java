package com.chadfunckes.alarm_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Chad on 12/15/2015.
 */
public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();

    }

}
