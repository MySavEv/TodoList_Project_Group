package com.example.todolist_project_group;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private int id;
    private String title;
    private String description;
    private long datetime;
    private int status;
    private int isNoti;

    public Task(int id, String title, String description, long datetime, int status,int isNoti) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.status = status;
        this.isNoti = isNoti;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getTime() {
        return datetime;
    }

    public String getDate(){
        return sdf.format(new Date(this.datetime));
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public boolean getIsNoti() {
        return isNoti == 1;
    }

    public static long DateStringToTimeStamp(String dateString) {
        try {
            return sdf.parse(dateString).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public PendingIntent getPendingIntent(Context context){

        Intent intent = new Intent(context, TaskAlarmReceiver.class);
        intent.putExtra("Title",this.getTitle());
        intent.putExtra("message",this.getDescription());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(context, getId(), intent,PendingIntent.FLAG_IMMUTABLE);

        }else {
            return PendingIntent.getBroadcast(context, getId(), intent,PendingIntent.FLAG_UPDATE_CURRENT);

        }
    }
}