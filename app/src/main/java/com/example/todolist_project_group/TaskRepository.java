package com.example.todolist_project_group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepository {
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DatabaseHelper dbHelper;

    public TaskRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertTask(String title, String description, Date date,boolean status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", dateFormat.format(date));
        values.put("status", status);

        // เพิ่มข้อมูลลงตาราง tasks
        long id = db.insert("tasks", null, values);
        db.close();
        return id;
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Task t = new Task(cursor.getString(1),cursor.getString(2),cursor.getString(3),Boolean.parseBoolean(cursor.getString(4)));
                tasks.add(t); // เพิ่ม title ลง List

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}