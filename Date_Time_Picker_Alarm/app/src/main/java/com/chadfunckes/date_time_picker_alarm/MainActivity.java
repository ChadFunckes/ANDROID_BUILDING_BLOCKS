package com.chadfunckes.date_time_picker_alarm;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getFragmentManager();
    static Context context;
    static int alYear, alMonth, alMin, alHour, alDay;
    final static int RQS_1 = 1; // the id for the alarm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    public void showDatePicker(View v){
        DialogFragment frag = new DatePickerFragment();
        frag.show(fm, "fsdfsdf");
    }

    public void showTimePickerDialog(View v) {
        Log.d("", "time picker hit");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(fm, "timePicker");
    }

    public void setAlarm(View v){
        Calendar cal = (Calendar) Calendar.getInstance().clone(); // get a cloned calendar object
        cal.set(alYear, alMonth, alDay, alHour, alMin, 0); // set it to the values needed

        Intent intent = new Intent(getBaseContext(), AlarmReciever.class); // set the alarm reciever
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0); // set the handling intent
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); // get an instance of the alarm service
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent); // set the alarm
    }



}
