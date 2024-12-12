package com.example.todolist_project_group;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

            Toast.makeText(context, R.string.add_alarm, Toast.LENGTH_SHORT).show();

            ComponentName receiver = new ComponentName(context,SampleBootReceiver.class);
            PackageManager pm = context.getPackageManager();
            pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);

    }

    public static void calcleAlarm(Context context,Task task){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent alarmIntent = task.getPendingIntent(context);

        alarmManager.cancel(alarmIntent);

        Toast.makeText(context, R.string.cancel_alarm, Toast.LENGTH_SHORT).show();

    }
}
