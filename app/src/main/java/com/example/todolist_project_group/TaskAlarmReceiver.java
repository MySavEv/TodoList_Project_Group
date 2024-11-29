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
import java.util.Locale;

public class TaskAlarmReceiver extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        service.putExtra("message", "ถึงเวลาทำงานแล้ว!");
        context.startService(service);
    }

    public void setAlarm(Context context,Task task){


        try{
            String dateString = task.getDate() + " "+ task.getTime();
            Date date = dateFormat.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TaskAlarmReceiver.class);
            intent.putExtra("msg",task.getTitle());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                alarmIntent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_IMMUTABLE);

            }else {
                alarmIntent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            }

            if (date == null){
                return;
            }
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 2*1000, alarmIntent);

            Toast.makeText(context, "Set Alarm", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.d("SAVE","Date parse Error");
        }
    }
}
