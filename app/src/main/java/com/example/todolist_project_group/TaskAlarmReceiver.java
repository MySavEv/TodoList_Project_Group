package com.example.todolist_project_group;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        service.putExtra("Title", intent.getStringExtra("Title"));
        service.putExtra("message", intent.getStringExtra("message"));

        context.startService(service);
    }

    public static void setAlarm(Context context,Task task){
            long time = task.getTime();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmIntent = task.getPendingIntent(context);

            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time, alarmIntent);

            Toast.makeText(context, "Set Alarm", Toast.LENGTH_LONG).show();

    }

    public static void calcleAlarm(Context context,Task task){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent alarmIntent = task.getPendingIntent(context);

        alarmManager.cancel(alarmIntent);

        Toast.makeText(context, "Cancel Alarm", Toast.LENGTH_LONG).show();

    }
}
