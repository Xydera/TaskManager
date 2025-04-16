package com.richo.taskmanagerapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.richo.taskmanagerapp.model.Task;
import com.richo.taskmanagerapp.database.TaskDatabase;

public class AddEditTaskActivity extends AppCompatActivity {

    EditText etTitle, etDesc, etDueDate;
    Button btnSave;

    Task taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("task_id")) {
            int taskId = getIntent().getIntExtra("task_id", -1);
            taskToEdit = TaskDatabase.getInstance(this).taskDao().getAllTasks()
                    .stream().filter(t -> t.id == taskId).findFirst().orElse(null);

            if (taskToEdit != null) {
                etTitle.setText(taskToEdit.title);
                etDesc.setText(taskToEdit.description);
                etDueDate.setText(taskToEdit.dueDate);
            }
        }


        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String due = etDueDate.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(due)) {
                Toast.makeText(this, "Title and Due Date are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (taskToEdit != null) {
                taskToEdit.title = title;
                taskToEdit.description = desc;
                taskToEdit.dueDate = due;
                TaskDatabase.getInstance(this).taskDao().updateTask(taskToEdit);
            } else {
                Task newTask = new Task();
                newTask.title = title;
                newTask.description = desc;
                newTask.dueDate = due;
                TaskDatabase.getInstance(this).taskDao().insertTask(newTask);
            }

            finish();
        });
    }
}