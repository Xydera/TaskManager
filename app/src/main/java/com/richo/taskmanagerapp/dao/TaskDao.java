package com.richo.taskmanagerapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.richo.taskmanagerapp.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);


    @Query("SELECT * FROM Task WHERE id = :taskId")
    Task getTaskById(int taskId);

    @Query("SELECT * FROM Task ORDER BY dueDate ASC")
    List<Task> getAllTasks();


}
