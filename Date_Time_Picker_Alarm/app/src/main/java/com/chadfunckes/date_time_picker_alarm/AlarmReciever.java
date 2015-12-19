package com.chadfunckes.date_time_picker_alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {

    public static final int NOTOFICATION_ID = 5453;

    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(k1, MainActivity.class);

        // this puts the intent into the stackbuilder (required to put an intent in the notification)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(k1);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        // this sets up the pending intent for the nitifcation manager and updates the notification with the same ID
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // this builds the notification, sets the message, the icon...etc...
        // see developer/reference/android/app/notification.builder.html for all options here
            Notification notification = new Notification.Builder(k1)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Test notification")
                    .setContentText("some shit here")
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .build();

        // get the systems notification manager
        NotificationManager notificationManager = (NotificationManager) k1.getSystemService(k1.NOTIFICATION_SERVICE);

        //send the actual notification
        notificationManager.notify(NOTOFICATION_ID, notification);
    }

}
