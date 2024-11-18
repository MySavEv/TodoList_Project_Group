package com.example.todolist_project_group;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String title;
    private String description;
    private String date;
    private boolean status;

    public Task(String title, String description, Date date, boolean status) {
        this.title = title;
        this.description = description;
        this.date = dateFormat.format(date);
        this.status = status;
    }

    public Task(String title, String description, String date, boolean status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }
}