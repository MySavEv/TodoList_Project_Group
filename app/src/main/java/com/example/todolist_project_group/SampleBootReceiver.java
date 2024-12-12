package com.example.todolist_project_group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class SampleBootReceiver extends BroadcastReceiver {
    TaskRepository taskRepository;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            taskRepository = new TaskRepository(context);
            List<Task> tasks = taskRepository.getAllTasks();

            tasks.forEach(item->{
                if(item.getIsNoti()){
                    TaskAlarmReceiver.setAlarm(context,item);
                }
            });

        }
    }
}
