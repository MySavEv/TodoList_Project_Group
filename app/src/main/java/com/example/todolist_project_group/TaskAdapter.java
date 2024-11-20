package com.example.todolist_project_group;// TaskAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                holder.itemView.getContext(),
                R.array.planets_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.textDate.setText(task.getDate());
        holder.textStatus.setAdapter(adapter);

        switch (task.getStatus()){
            case 0:
                holder.textStatus.setSelection(0);
                holder.itemView.setBackgroundResource(R.drawable.notyet_background);
            case 1:
                holder.textStatus.setSelection(1);
                holder.itemView.setBackgroundResource(R.drawable.inprocess_background);
            case 2:
                holder.textStatus.setSelection(2);
                holder.itemView.setBackgroundResource(R.drawable.complete_background);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void removeItem(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView textDate;
        Spinner textStatus;


        TaskViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.textTaskTitle);
            descriptionTextView = view.findViewById(R.id.textTaskDescription);
            textDate = view.findViewById(R.id.textDate);
            textStatus = (Spinner)view.findViewById(R.id.textStatus);

        }
    }
}
