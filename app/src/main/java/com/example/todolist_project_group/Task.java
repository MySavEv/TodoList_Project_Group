package com.example.todolist_project_group;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH:mm");

    private int id;
    private String title;
    private String description;
    private String date;
    private String time;
    private int status;
    private int isNoti;

    public Task(int id, String title, String description, Date date, int status , String time,int isNoti) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = dateFormat.format(date);
        this.status = status;
        this.time = time;
        this.isNoti = isNoti;
    }

    public Task(int id, String title, String description, String date, int status, String time,int isNoti) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
        this.time = time;
        this.isNoti = isNoti;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getTime(){
        return this.time;
    }

}