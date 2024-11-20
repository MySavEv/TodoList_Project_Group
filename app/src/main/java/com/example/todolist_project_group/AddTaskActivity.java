package com.example.todolist_project_group;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTextTaskTitle, editTextTaskDescription;
    private Button buttonSaveTask;
    private CalendarView calendarView;
    private PopupWindow calendarPopupWindow;
    private TextView editTextDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        createCalendarPopup();

        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarPopupWindow.showAsDropDown(view, 0, 0);
            }
        });


        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task details
                String title = editTextTaskTitle.getText().toString().trim();
                String description = editTextTaskDescription.getText().toString().trim();
                String duedate = editTextDueDate.getText().toString().trim();

                // Create an intent to send the data back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("TASK_TITLE", title);
                resultIntent.putExtra("TASK_DESCRIPTION", description);
                resultIntent.putExtra("TASK_DUEDATE", duedate);

                // Set result and finish the activity
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    // สร้าง PopupWindow สำหรับแสดง CalendarView
    private void createCalendarPopup() {
        // ใช้ LayoutInflater เพื่อโหลด layout สำหรับ PopupWindow
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_calendar, null);

        // หาค่าของ CalendarView และ TextView ใน PopupWindow
        calendarView = popupView.findViewById(R.id.calendarView);

        // กำหนดการตั้งค่าการเลือกวันที่
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // เมื่อเลือกวันที่จาก CalendarView
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            editTextDueDate.setText(selectedDate);
            // ปิด PopupWindow หลังจากเลือกวันที่
            calendarPopupWindow.dismiss();
        });

        // สร้าง PopupWindow จาก popupView
        calendarPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // กำหนดให้ PopupWindow ไม่สามารถขยายไปนอกขอบเขตของหน้าจอ
        calendarPopupWindow.setFocusable(true); // ทำให้สามารถโฟกัสได้
        calendarPopupWindow.setOutsideTouchable(true); // ทำให้สามารถคลิกนอก PopupWindow เพื่อปิดมัน
    }
}