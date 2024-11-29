package com.example.todolist_project_group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepository {
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy");

    private DatabaseHelper dbHelper;

    public TaskRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertTask(String title, String description, Date date,int status) {
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

    public long insertTask(String title, String description, long datetime,int status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("datetime", datetime);
        values.put("status", status);
        values.put("isNoti", 0);

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
                Task t = new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getInt(4),cursor.getInt(5));
                tasks.add(t); // เพิ่ม title ลง List

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public int updateStatus(int id,int status){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "UPDATE tasks\n" +
                "SET status = ?\n" +
                "WHERE id = ?;";
        SQLiteStatement stmt = db.compileStatement(query);
        stmt.bindLong(1,status);
        stmt.bindLong(2,id);

        return stmt.executeUpdateDelete();
    }

    public int deleteTask(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "DELETE FROM tasks WHERE id = ?";
        SQLiteStatement stmt = db.compileStatement(query);

        stmt.bindLong(1, id);

        return stmt.executeUpdateDelete();
    }
}