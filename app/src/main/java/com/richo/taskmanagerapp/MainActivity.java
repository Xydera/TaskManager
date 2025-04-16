package com.richo.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.richo.taskmanagerapp.database.TaskDatabase;
import com.richo.taskmanagerapp.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskActionListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddTask = findViewById(R.id.btnAddTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadTasks();

        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddEditTaskActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks(); // Refresh tasks when returning
    }

    private void loadTasks() {
        taskList = TaskDatabase.getInstance(getApplicationContext()).taskDao().getAllTasks();
        adapter = new TaskAdapter(taskList, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDeleteTask(Task task) {
        TaskDatabase.getInstance(getApplicationContext()).taskDao().deleteTask(task);
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
        loadTasks(); // Refresh list
    }

    @Override
    public void onEditTask(Task task) {
        Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
        intent.putExtra("task_id", task.id); // ✅ THIS is what makes editing work
        startActivity(intent);
    }
}
