package com.chadfunckes.date_time_picker_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootCompletedReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // DO WORK HERE TO RE-ESTABLISH ALARMS
        Log.w("TEST ON BOOT RECIEVER", "TEST ON BOOT RECIEVER HIT...");
    }
}
