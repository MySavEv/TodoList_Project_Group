package com.example.todolist_project_group;

import android.annotation.SuppressLint;

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

    public static long DateStringToTimeStamp(String dateString) {
        try {
            return sdf.parse(dateString).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}