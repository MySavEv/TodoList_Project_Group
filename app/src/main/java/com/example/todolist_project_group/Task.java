package com.example.todolist_project_group;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    private int id;
    private String title;
    private String description;
    private String date;
    private int status;

    public Task(int id, String title, String description, Date date, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = dateFormat.format(date);
        this.status = status;
    }

    public Task(int id, String title, String description, String date, int status) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}