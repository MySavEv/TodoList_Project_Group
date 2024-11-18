package com.example.todolist_project_group;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTextTaskTitle, editTextTaskDescription;
    private Button buttonSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task details
                String title = editTextTaskTitle.getText().toString().trim();
                String description = editTextTaskDescription.getText().toString().trim();

                // Create an intent to send the data back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("TASK_TITLE", title);
                resultIntent.putExtra("TASK_DESCRIPTION", description);

                // Set result and finish the activity
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}