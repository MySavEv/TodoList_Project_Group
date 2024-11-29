package com.example.todolist_project_group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TASK_REQUEST_CODE = 1;

    private TaskRepository taskRepository;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private boolean doubleBackToExitPressedOnce ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        taskRepository = new TaskRepository(this);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList,taskRepository);
        recyclerViewTasks.setAdapter(taskAdapter);

        updateTaskLists();
        taskAdapter.notifyDataSetChanged();


        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent,1);
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No movement allowed
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                //Alert ask use for delete task
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Proceed with deletion
                            taskRepository.deleteTask(taskList.get(position).getId());
                            taskAdapter.removeItem(position);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // Undo the swipe (reverting the swipe action)

                            taskAdapter.notifyItemChanged(position);
                        })
                        .show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewTasks);
    }

    private void updateTaskLists(){
        taskList.removeAll(taskList);
        taskList.addAll(taskRepository.getAllTasks());
        Collections.reverse(taskList);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // ถ้าผู้ใช้กดปุ่ม Back 2 ครั้งในช่วงเวลาที่กำหนด ให้ออกจาก Activity
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "กด Back อีกครั้งเพื่อออกจากแอป", Toast.LENGTH_SHORT).show();

        // ใช้ Handler เพื่อรีเซ็ตการกดปุ่ม back หลังจาก 2 วินาที
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000); // 2 วินาที
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Retrieve the task details from the intent
            String title = data.getStringExtra("TASK_TITLE");
            String description = data.getStringExtra("TASK_DESCRIPTION");
            String duedatetime = data.getStringExtra("TASK_DUEDATETIME");

            // Add the new task to the list and update the adapter
            taskRepository.insertTask(title,description,Task.DateStringToTimeStamp(duedatetime),0);
            updateTaskLists();

            taskAdapter.notifyDataSetChanged();
        }
    }
}